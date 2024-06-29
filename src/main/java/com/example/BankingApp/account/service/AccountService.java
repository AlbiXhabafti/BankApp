package com.example.BankingApp.account.service;

import com.example.BankingApp.account.dto.AccountDto;
import com.example.BankingApp.account.dto.AccountResponseDto;

import java.util.List;


public interface AccountService {

    void add(AccountDto accountDto,String email);

    void update(String iban,Boolean approved, String email);

    List<AccountResponseDto> get(String email);

}
