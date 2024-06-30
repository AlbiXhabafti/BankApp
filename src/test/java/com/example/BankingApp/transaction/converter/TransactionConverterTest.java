package com.example.BankingApp.transaction.converter;

import com.example.BankingApp.account.enums.CurrencyEnum;
import com.example.BankingApp.account.model.Account;
import com.example.BankingApp.transaction.dto.TransactionDto;
import com.example.BankingApp.transaction.dto.TransactionResponseDto;
import com.example.BankingApp.transaction.enums.TransactionType;
import com.example.BankingApp.transaction.model.Transaction;
import com.example.BankingApp.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class TransactionConverterTest {
    @InjectMocks
    TransactionConverter transactionConverter;

    @Test
    void convertToTransactionResponseDto(){

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
        targetAccount.setId(2);
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
        dto.setToIban("1111");
        dto.setFromIban("1234");

        var result = transactionConverter.convertToTransactionResponseDto(transaction);

        Assertions.assertEquals(dto.getFromIban(),result.getFromIban());
        Assertions.assertEquals(dto.getType(),result.getType());
        Assertions.assertEquals(dto.getCurrency(),result.getCurrency());
        Assertions.assertEquals(dto.getAmount(),result.getAmount());
        Assertions.assertEquals(dto.getToIban(),result.getToIban());
    }
    @Test
    void convertToTransaction_credit(){

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
        targetAccount.setId(2);
        targetAccount.setIban("1234");
        targetAccount.setBalance(700.8);
        targetAccount.setCreatedBy(user);
        targetAccount.setApproved(Boolean.TRUE);

        TransactionDto dto = new TransactionDto();
        dto.setAmount(100.0);
        dto.setFromIban("1234");
        dto.setToIban("1111");
        dto.setCurrency("Euro");
        dto.setType("CREDIT");

        Transaction transaction = new Transaction();
        transaction.setAmount(100.0);
        transaction.setCurrencyEnum(CurrencyEnum.EUR);
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setAccount(account);
        transaction.setToAccount(targetAccount);
        transaction.setCreatedBy(user);

        var result = transactionConverter.convertToTransaction(dto,account,targetAccount);
        Assertions.assertEquals(transaction.getTransactionType(),result.getTransactionType());
        Assertions.assertEquals(transaction.getAmount(),result.getAmount());
        Assertions.assertEquals(transaction.getCurrencyEnum(),result.getCurrencyEnum());

    }
    @Test
    void convertToTransaction_debit(){

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
        targetAccount.setId(2);
        targetAccount.setIban("1234");
        targetAccount.setBalance(700.8);
        targetAccount.setCreatedBy(user);
        targetAccount.setApproved(Boolean.TRUE);

        TransactionDto dto = new TransactionDto();
        dto.setAmount(100.0);
        dto.setFromIban("1234");
        dto.setToIban("1111");
        dto.setCurrency("Euro");
        dto.setType("DEBIT");

        Transaction transaction = new Transaction();
        transaction.setAmount(100.0);
        transaction.setCurrencyEnum(CurrencyEnum.EUR);
        transaction.setTransactionType(TransactionType.DEBIT);
        transaction.setAccount(account);
        transaction.setToAccount(targetAccount);
        transaction.setCreatedBy(user);

        var result = transactionConverter.convertToTransaction(dto,account,targetAccount);
        Assertions.assertEquals(transaction.getTransactionType(),result.getTransactionType());
        Assertions.assertEquals(transaction.getAmount(),result.getAmount());
        Assertions.assertEquals(transaction.getCurrencyEnum(),result.getCurrencyEnum());

    }

}