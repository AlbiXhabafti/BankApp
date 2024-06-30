package com.example.BankingApp.transaction.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponseDto {
    private Double amount;
    private String currency;
    private String type;
    private String fromIban;
    private String toIban;

}
