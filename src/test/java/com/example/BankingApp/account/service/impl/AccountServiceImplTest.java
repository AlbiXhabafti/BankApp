package com.example.BankingApp.account.service.impl;

import com.example.BankingApp.account.converter.AccountConverter;
import com.example.BankingApp.account.dto.AccountDto;
import com.example.BankingApp.account.dto.AccountResponseDto;
import com.example.BankingApp.account.enums.CurrencyEnum;
import com.example.BankingApp.account.model.Account;
import com.example.BankingApp.account.repository.AccountRepository;
import com.example.BankingApp.exception.NoResultFoundException;
import com.example.BankingApp.user.model.User;
import com.example.BankingApp.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {
    @InjectMocks
    AccountServiceImpl accountService;

    @Mock
    AccountRepository accountRepository;

    @Mock
    AccountConverter accountConverter;

    @Mock
    UserRepository userRepository;

    @Test
    void add(){
        String email = "test@gmail.com";

        User user = new User();
        user.setId(1);
        user.setEmail(email);


        AccountDto accountDto = new AccountDto();
        accountDto.setIban("1234");
        accountDto.setCurrency("Euro");
        accountDto.setBalance(700.5);

        Account account = new Account();
        account.setId(1);
        account.setIban("1234");
        account.setBalance(700.5);
        account.setCurrencyEnum(CurrencyEnum.EUR);
        account.setCreatedBy(user);

        Mockito.when(accountConverter.convertToAccount(accountDto)).thenReturn(account);
        Mockito.when(userRepository.findByEmailAndDeletedFalse(email)).thenReturn(Optional.of(user));

        accountService.add(accountDto,email);
        verify(accountRepository, Mockito.times(1)).save(account);

    }
    @Test
    void add_throwsException(){
        String email = "test@gmail.com";

        AccountDto accountDto = new AccountDto();
        accountDto.setIban("1234");
        accountDto.setCurrency("Euro");
        accountDto.setBalance(700.5);

        Mockito.when(accountConverter.convertToAccount(accountDto)).thenReturn(null);

        assertThrows(NoResultFoundException.class,()-> accountService.add(accountDto,email));
    }
    @Test
    void get(){
        String email = "albi@gmail.com";
        User user = new User();
        user.setId(1);
        user.setEmail(email);


       Account account = new Account();
       account.setId(1);
       account.setIban("1234");
       account.setBalance(700.8);
       account.setCreatedBy(user);

        AccountResponseDto dto = new AccountResponseDto();
        dto.setIban("1234");
        dto.setBalance(700.8);
        dto.setCurrency("Euro");
        dto.setApproved(Boolean.TRUE);



        Mockito.when(userRepository.existsByEmail(email)).thenReturn(Boolean.TRUE);
        Mockito.when(accountRepository.findByCreatedByEmail(email)).thenReturn(List.of(account));
        Mockito.when(accountConverter.convertToAccountResponseDto(account)).thenReturn(dto);

        var response = accountService.get(email);

        Assertions.assertEquals(response.get(0).getBalance(),dto.getBalance());
        Assertions.assertEquals(response.get(0).getIban(),dto.getIban());
        Assertions.assertEquals(response.get(0).getCurrency(),dto.getCurrency());
        Assertions.assertEquals(response.get(0).getApproved(),dto.getApproved());
    }
    @Test
    void get_throws_exception(){
        String email = "albi@gmail.com";

        Mockito.when(userRepository.existsByEmail(email)).thenReturn(Boolean.FALSE);
        assertThrows(NoResultFoundException.class,()-> accountService.get(email));

    }


}