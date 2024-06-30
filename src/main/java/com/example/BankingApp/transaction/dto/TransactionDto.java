package com.example.BankingApp.transaction.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {

    @NotNull
    private Double amount;

    @NotNull
    private String fromIban;

    @NotNull
    private String toIban;

    private String currency;


    private String type;

}
