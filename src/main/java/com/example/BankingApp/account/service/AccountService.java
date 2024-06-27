package com.example.BankingApp.account.service;

import com.example.BankingApp.account.dto.AccountDto;
import com.example.BankingApp.account.dto.AccountResponseDto;

import java.util.List;


public interface AccountService {
    Integer add(AccountDto accountDto, String userId);
    void update(Integer id,Boolean approved);
    List<AccountResponseDto> get(String email);

}
