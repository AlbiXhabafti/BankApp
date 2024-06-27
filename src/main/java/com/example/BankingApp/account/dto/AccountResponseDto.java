package com.example.BankingApp.account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponseDto {
    private String iban;
    private String currency;
    private Double balance;
    private Boolean approved;

}
