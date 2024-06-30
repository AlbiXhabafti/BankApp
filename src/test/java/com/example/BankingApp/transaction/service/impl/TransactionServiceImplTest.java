package com.example.BankingApp.transaction.service.impl;

import com.example.BankingApp.account.enums.CurrencyEnum;
import com.example.BankingApp.account.model.Account;
import com.example.BankingApp.account.repository.AccountRepository;
import com.example.BankingApp.transaction.converter.TransactionConverter;
import com.example.BankingApp.transaction.dto.TransactionDto;
import com.example.BankingApp.transaction.dto.TransactionResponseDto;
import com.example.BankingApp.transaction.enums.TransactionType;
import com.example.BankingApp.transaction.model.Transaction;
import com.example.BankingApp.transaction.repository.TransactionRepository;
import com.example.BankingApp.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {
    @Mock
    TransactionRepository transactionRepository;

    @Mock
    AccountRepository accountRepository;

    @Mock
    TransactionConverter transactionConverter;

    @InjectMocks
    TransactionServiceImpl transactionService;

    @Test
    void add(){

        String email = "albi@gmail.com";


        User user = new User();
        user.setId(1);
        user.setEmail(email);

        TransactionDto dto = new TransactionDto();
        dto.setAmount(100.0);
        dto.setFromIban("1234");
        dto.setToIban("1111");
        dto.setCurrency("Euro");
        dto.setType("CREDIT");

        Account account = new Account();
        account.setId(1);
        account.setIban("1234");
        account.setBalance(700.8);
        account.setCreatedBy(user);
        account.setApproved(Boolean.TRUE);

        Account targetAccount = new Account();
        account.setId(1);
        account.setIban("1111");
        account.setBalance(700.8);
        account.setCreatedBy(user);
        account.setApproved(Boolean.TRUE);

        Transaction transaction = new Transaction();
        transaction.setAmount(100.0);
        transaction.setCurrencyEnum(CurrencyEnum.EUR);
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setAccount(account);
        transaction.setToAccount(targetAccount);
        transaction.setCreatedBy(user);

        Mockito.when(accountRepository.findByIbanAndCreatedByEmail(dto.getFromIban(),email)).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.findByIban(dto.getToIban())).thenReturn(Optional.of(targetAccount));
        Mockito.when(transactionConverter.convertToTransaction(dto,account,targetAccount)).thenReturn(transaction);
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountRepository.save(targetAccount)).thenReturn(targetAccount);
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);

        transactionService.add(dto,email);

        verify(accountRepository, Mockito.times(1)).save(account);
        verify(accountRepository, Mockito.times(1)).save(targetAccount);
        verify(transactionRepository, Mockito.times(1)).save(transaction);

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
        account.setApproved(Boolean.TRUE);

        Account targetAccount = new Account();
        targetAccount.setId(1);
        targetAccount.setIban("1111");
        targetAccount.setBalance(700.8);
        targetAccount.setCreatedBy(user);
        targetAccount.setApproved(Boolean.TRUE);


        Transaction transaction = new Transaction();
        transaction.setAmount(100.0);
        transaction.setCurrencyEnum(CurrencyEnum.EUR);
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setAccount(account);
        transaction.setToAccount(targetAccount);
        transaction.setCreatedBy(user);

        TransactionResponseDto dto = new TransactionResponseDto();
        dto.setType("CREDIT");
        dto.setCurrency("Euro");
        dto.setAmount(100.0);
        dto.setToIban("1234");
        dto.setFromIban("1111");

        when(transactionRepository.findByCreatedByEmail(email)).thenReturn(List.of(transaction));
        when(transactionConverter.convertToTransactionResponseDto(transaction)).thenReturn(dto);

        var response = transactionService.get(email);
        Assertions.assertEquals(dto.getAmount(),response.get(0).getAmount());
        Assertions.assertEquals(dto.getCurrency(),response.get(0).getCurrency());
        Assertions.assertEquals(dto.getToIban(),response.get(0).getToIban());
        Assertions.assertEquals(dto.getFromIban(),response.get(0).getFromIban());
    }


}