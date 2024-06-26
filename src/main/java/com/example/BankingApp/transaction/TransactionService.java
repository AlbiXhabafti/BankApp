package com.example.BankingApp.transaction;

import com.example.BankingApp.transaction.dto.TransactionDto;
import com.example.BankingApp.transaction.model.Transaction;

public interface TransactionService {
    Integer add(TransactionDto transactionDto);
}
