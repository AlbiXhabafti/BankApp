package com.example.BankingApp.card.converter;

import com.example.BankingApp.card.dto.NewDebitCardDto;
import com.example.BankingApp.card.model.DebitCard;
import org.springframework.stereotype.Component;

@Component
public class DebitCardConverter {
    public DebitCard convertToDebitCard(NewDebitCardDto dto){
        DebitCard debitCard = new DebitCard();
        debitCard.setCardNumber(dto.getCardNumber());
        debitCard.setCardholderName(dto.getCardholderName());
        debitCard.setExpirationDate(dto.getExpirationDate());
        return debitCard;
    }
}
