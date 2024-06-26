package com.example.BankingApp.card.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
public class NewDebitCardDto {

    private String cardNumber;

    private String cardholderName;

    private String expirationDate;

    @NonNull
    private Double salary;

    private String iban;

}
