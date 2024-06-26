package com.example.BankingApp.account.converter;

import com.example.BankingApp.account.dto.NewAccountDto;
import com.example.BankingApp.account.enums.CurrencyEnum;
import com.example.BankingApp.account.model.Account;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AccountConverter {

    public Account convertToAccount(NewAccountDto dto){
        Account account = new Account();
        account.setIban(dto.getIban());
        account.setBalance(dto.getBalance());
        account.setCurrencyEnum(CurrencyEnum.fromValue(dto.getCurrency()));
        return account;
    }

}
