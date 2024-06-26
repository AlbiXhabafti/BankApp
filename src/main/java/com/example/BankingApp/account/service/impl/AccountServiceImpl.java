package com.example.BankingApp.account.service.impl;

import com.example.BankingApp.account.converter.AccountConverter;
import com.example.BankingApp.account.dto.NewAccountDto;
import com.example.BankingApp.account.model.Account;
import com.example.BankingApp.account.repository.AccountRepository;
import com.example.BankingApp.account.service.AccountService;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;

    public AccountServiceImpl(AccountRepository accountRepository, AccountConverter accountConverter) {
        this.accountRepository = accountRepository;
        this.accountConverter = accountConverter;
    }

    @Override
    public Integer addAccount(NewAccountDto newAccountDto) {
        Account account = accountConverter.convertToAccount(newAccountDto);
        accountRepository.save(account);
        return account.getId();
    }

    @Override
    public void update(Integer id, Boolean approved) {
        Account account = accountRepository.findById(id).orElseThrow(()->new NoResultException("user is not found"));
        account.setApproved(approved);
        accountRepository.save(account);
    }
}
