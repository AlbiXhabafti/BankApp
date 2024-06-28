package com.example.BankingApp.transaction.converter;

import com.example.BankingApp.account.enums.CurrencyEnum;
import com.example.BankingApp.account.model.Account;
import com.example.BankingApp.transaction.dto.TransactionDto;
import com.example.BankingApp.transaction.dto.TransactionResponseDto;
import com.example.BankingApp.transaction.enums.TransactionType;
import com.example.BankingApp.transaction.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter {
    public TransactionResponseDto convertToTransactionDto(Transaction transaction){
        TransactionResponseDto dto = new TransactionResponseDto();
        dto.setType(transaction.getTransactionType().getValue());
        dto.setCurrency(transaction.getTransactionType().getValue());
        dto.setAmount(transaction.getAmount());
        return dto;
    }
    public Transaction convertToTransaction(TransactionDto dto, Account account, Account targetAccount ){
        Transaction transaction = new Transaction();
        transaction.setCurrencyEnum(CurrencyEnum.fromValue(dto.getCurrency()));
        transaction.setTransactionType(TransactionType.fromValue(dto.getType()));
        transaction.setAmount(dto.getAmount());
        if (dto.getType().equals(TransactionType.DEBIT.getValue())){
            account.setBalance(subtractedBalance(account.getBalance(),dto.getAmount(), account.getIban()));
            targetAccount.setBalance(addedBalance(targetAccount.getBalance(),dto.getAmount()));
        }else {
            account.setBalance(addedBalance(account.getBalance(), dto.getAmount()));
            targetAccount.setBalance(subtractedBalance(targetAccount.getBalance(), dto.getAmount(), targetAccount.getIban()));
        }
        transaction.setAccount(account);
        transaction.setTargetAccount(targetAccount);
        return transaction;
    }
    private Double subtractedBalance(Double balance, Double amount,String iban){
        if (balance>amount){
            return balance-amount;
        }else
            throw new ArithmeticException("The balance of account with iban ".concat(iban).concat(" is less than amount"));

    }
    private Double addedBalance(Double balance,Double amount){
        return balance+amount;
    }



}
