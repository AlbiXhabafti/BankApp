package com.example.BankingApp.card.dto;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DebitCardResponseDto {
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private Boolean approved;
    private String disapproveReason;

}
