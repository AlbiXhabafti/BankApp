package com.example.BankingApp.transaction.converter;

import com.example.BankingApp.transaction.dto.TransactionResponseDto;
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

}
