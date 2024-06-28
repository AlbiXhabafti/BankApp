package com.example.BankingApp.transaction.service.impl;

import com.example.BankingApp.account.model.Account;
import com.example.BankingApp.account.repository.AccountRepository;
import com.example.BankingApp.exception.NoResultFoundException;
import com.example.BankingApp.transaction.converter.TransactionConverter;
import com.example.BankingApp.transaction.dto.TransactionDto;
import com.example.BankingApp.transaction.dto.TransactionResponseDto;
import com.example.BankingApp.transaction.model.Transaction;
import com.example.BankingApp.transaction.repository.TransactionRepository;
import com.example.BankingApp.transaction.service.TransactionService;
import com.example.BankingApp.user.model.User;
import com.example.BankingApp.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private  final TransactionConverter transactionConverter;


    public TransactionImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, UserRepository userRepository, TransactionConverter transactionConverter) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionConverter = transactionConverter;
    }

    @Override
    public Integer add(TransactionDto dto, String email) {

        Account account = accountRepository.findByIbanAndCreatedByEmail(dto.getIban(),email)
                .orElseThrow(()-> new NoResultFoundException("Please verify iban and try again"));

        Account targetAccount = accountRepository.findByIban(dto.getTargetIban()).orElseThrow(()-> new NoResultFoundException("destination account doesn't exist"));

        Transaction transaction = transactionConverter.convertToTransaction(dto,account,targetAccount);
        transaction.setCreatedBy(account.getCreatedBy());

        accountRepository.save(account);
        accountRepository.save(targetAccount);
        transactionRepository.save(transaction);
        return transaction.getId();
    }

    @Override
    public List<TransactionResponseDto> get(String email) {
        List<Transaction>transactionList = transactionRepository.findByCreatedByEmail(email);
        return transactionList.stream().map(transactionConverter::convertToTransactionDto).toList();
    }
}
