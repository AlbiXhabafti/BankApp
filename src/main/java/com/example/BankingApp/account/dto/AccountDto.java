package com.example.BankingApp.account.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
    @NotNull
    private String iban;

    private String currency;

    private Double balance;

}
