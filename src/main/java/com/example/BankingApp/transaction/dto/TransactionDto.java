package com.example.BankingApp.transaction.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {

    private Double amount;

    private String iban;

    private String currency;


    private String type;

}
