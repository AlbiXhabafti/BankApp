package com.example.BankingApp.account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewAccountDto {

    private String iban;
    private String currency;
    private Double balance;

}
