package com.example.BankingApp.account.service.impl;

import com.example.BankingApp.account.converter.AccountConverter;
import com.example.BankingApp.account.dto.AccountDto;
import com.example.BankingApp.account.dto.AccountResponseDto;
import com.example.BankingApp.account.model.Account;
import com.example.BankingApp.account.repository.AccountRepository;
import com.example.BankingApp.account.service.AccountService;
import com.example.BankingApp.exception.NoResultFoundException;
import com.example.BankingApp.user.model.User;
import com.example.BankingApp.user.repository.UserRepository;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;
    private final UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository, AccountConverter accountConverter, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.accountConverter = accountConverter;
        this.userRepository = userRepository;
    }

    @Override
    public Integer add(AccountDto accountDto, String userEmail) {
        Account account = accountConverter.convertToAccount(accountDto);
        User user = userRepository.findByEmail(userEmail).orElseThrow(()-> new NoResultFoundException("account is not found"));
        account.setCreatedBy(user);
        accountRepository.save(account);
        return account.getId();
    }

    @Override
    public void update(Integer id, Boolean approved) {
        Account account = accountRepository.findById(id).orElseThrow(()->new NoResultFoundException("account is not found"));
        account.setApproved(approved);
        accountRepository.save(account);
    }

    @Override
    public List<AccountResponseDto> get(String email) {
        List<Account>accountList = accountRepository.findAccountByEmail(email);
        return accountList.stream().map(accountConverter::convertToAccountResponseDto).toList();
    }
}
