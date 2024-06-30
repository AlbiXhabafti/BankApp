package com.example.BankingApp.account.converter;

import com.example.BankingApp.account.dto.AccountDto;
import com.example.BankingApp.account.dto.AccountResponseDto;
import com.example.BankingApp.account.enums.CurrencyEnum;
import com.example.BankingApp.account.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class AccountConverterTest {
    @InjectMocks
    AccountConverter accountConverter;

    @Test
    void convertToAccount(){
        AccountDto accountDto = new AccountDto();
        accountDto.setIban("1234");
        accountDto.setCurrency("Euro");
        accountDto.setBalance(700.5);

        Account account = new Account();
        account.setId(1);
        account.setIban("1234");
        account.setBalance(700.5);
        account.setCurrencyEnum(CurrencyEnum.EUR);

        var response = accountConverter.convertToAccount(accountDto);
        Assertions.assertEquals(account.getBalance(),response.getBalance());
        Assertions.assertEquals(account.getCurrencyEnum(),response.getCurrencyEnum());
        Assertions.assertEquals(account.getIban(),response.getIban());
    }

    @Test
    void convertToAccountResponseDto(){

        Account account = new Account();
        account.setId(1);
        account.setIban("1234");
        account.setBalance(700.8);
        account.setApproved(Boolean.TRUE);
        account.setCurrencyEnum(CurrencyEnum.EUR);

        AccountResponseDto dto = new AccountResponseDto();
        dto.setIban("1234");
        dto.setBalance(700.8);
        dto.setCurrency("Euro");
        dto.setApproved(Boolean.TRUE);

        var response = accountConverter.convertToAccountResponseDto(account);

        Assertions.assertEquals(dto.getBalance(),response.getBalance());
        Assertions.assertEquals(dto.getCurrency(),response.getCurrency());
        Assertions.assertEquals(dto.getIban(),response.getIban());
        Assertions.assertEquals(dto.getApproved(),response.getApproved());

    }


}