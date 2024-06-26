package com.example.BankingApp.account.service;

import com.example.BankingApp.account.dto.NewAccountDto;
import org.springframework.stereotype.Service;


public interface AccountService {
    Integer addAccount(NewAccountDto newAccountDto);
    void update(Integer id,Boolean approved);

}
