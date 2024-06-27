package com.example.BankingApp.transaction.service.impl;

import com.example.BankingApp.account.enums.CurrencyEnum;
import com.example.BankingApp.account.model.Account;
import com.example.BankingApp.account.repository.AccountRepository;
import com.example.BankingApp.exception.NoResultFoundException;
import com.example.BankingApp.transaction.converter.TransactionConverter;
import com.example.BankingApp.transaction.dto.TransactionDto;
import com.example.BankingApp.transaction.dto.TransactionResponseDto;
import com.example.BankingApp.transaction.enums.TransactionType;
import com.example.BankingApp.transaction.model.Transaction;
import com.example.BankingApp.transaction.repository.TransactionRepository;
import com.example.BankingApp.transaction.service.TransactionService;
import com.example.BankingApp.user.model.User;
import com.example.BankingApp.user.repository.UserRepository;
import jakarta.persistence.NoResultException;
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
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        transaction.setCurrencyEnum(CurrencyEnum.fromValue(dto.getCurrency()));
        transaction.setTransactionType(TransactionType.fromValue(dto.getType()));

        Account account = accountRepository.findByIban(dto.getIban());
        User user = userRepository.findByEmail(email).orElseThrow(()->new NoResultFoundException("User is not found"));
        if (dto.getType().equals(TransactionType.DEBIT.getValue())){
            account.setBalance(subtractedBalance(account.getBalance(),dto.getAmount()));
        }else {
            account.setBalance(addedBalance(account.getBalance(), dto.getAmount()));
        }
        transaction.setCreatedBy(user);
        transaction.setAccount(account);
        accountRepository.save(account);
        transactionRepository.save(transaction);
        return transaction.getId();
    }

    @Override
    public List<TransactionResponseDto> get(String email) {
        userRepository.findByEmail(email).orElseThrow(() -> new NoResultFoundException("user is not found"));
        List<Transaction>transactionList = transactionRepository.findTransactionByEmail(email);
        return transactionList.stream().map(transactionConverter::convertToTransactionDto).toList();
    }

    private Double subtractedBalance(Double balance, Double amount){
        if (balance>amount){
            return balance-amount;
        }else
            throw new ArithmeticException("transaction is interrupted because balance is less than amount");

    }
    private Double addedBalance(Double balance,Double amount){
        return balance+amount;
    }
}
