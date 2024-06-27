package com.example.BankingApp.account.converter;

import com.example.BankingApp.account.dto.AccountDto;
import com.example.BankingApp.account.dto.AccountResponseDto;
import com.example.BankingApp.account.enums.CurrencyEnum;
import com.example.BankingApp.account.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter {
    public Account convertToAccount(AccountDto dto){
        Account account = new Account();
        account.setIban(dto.getIban());
        account.setBalance(dto.getBalance());
        account.setCurrencyEnum(CurrencyEnum.fromValue(dto.getCurrency()));
        return account;
    }
    public AccountResponseDto convertToAccountResponseDto(Account account){
        AccountResponseDto responseDto = new AccountResponseDto();
        responseDto.setCurrency(account.getCurrencyEnum().getValue());
        responseDto.setBalance(account.getBalance());
        responseDto.setIban(account.getIban());
        responseDto.setApproved(account.isApproved());
        return responseDto;
    }

}
