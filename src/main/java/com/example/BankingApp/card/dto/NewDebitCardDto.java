package com.example.BankingApp.card.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewDebitCardDto {

    private String cardNumber;

    private String cardholderName;

    private String expirationDate;

    @NotNull
    private Double salary;

    @NotNull
    private String iban;

}
