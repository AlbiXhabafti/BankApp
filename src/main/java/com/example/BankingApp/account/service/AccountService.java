package com.example.BankingApp.account.service;

import com.example.BankingApp.account.dto.AccountDto;
import com.example.BankingApp.account.dto.AccountResponseDto;

import java.util.List;


public interface AccountService {

    Integer add(AccountDto accountDto,String email);

    void update(Integer id,Boolean approved, String email);

    List<AccountResponseDto> get(String email);

}
