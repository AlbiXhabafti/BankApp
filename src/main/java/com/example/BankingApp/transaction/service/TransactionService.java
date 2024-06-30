package com.example.BankingApp.transaction.service;

import com.example.BankingApp.transaction.dto.TransactionDto;
import com.example.BankingApp.transaction.dto.TransactionResponseDto;

import java.util.List;

public interface TransactionService {
    void add(TransactionDto transactionDto,String email);
    List<TransactionResponseDto> get(String email);

}
