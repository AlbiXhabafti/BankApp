package com.example.BankingApp.card.converter;

import com.example.BankingApp.card.dto.DebitCardResponseDto;
import com.example.BankingApp.card.dto.NewDebitCardDto;
import com.example.BankingApp.card.model.DebitCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class DebitCardConverterTest {
    @InjectMocks
    DebitCardConverter debitCardConverter;
    @Test
    void convertToDebitCard(){
        NewDebitCardDto dto = new NewDebitCardDto();
        dto.setCardNumber("1111");
        dto.setIban("1234");
        dto.setCardholderName("albi");
        dto.setExpirationDate("06/25");

        DebitCard debitCard = new DebitCard();
        debitCard.setId(1);
        debitCard.setCardNumber("1111");
        debitCard.setCardholderName("albi");
        debitCard.setExpirationDate("06/25");

        var result = debitCardConverter.convertToDebitCard(dto);
        Assertions.assertEquals(debitCard.getCardNumber(),result.getCardNumber());
        Assertions.assertEquals(debitCard.getCardholderName(),result.getCardholderName());
        Assertions.assertEquals(debitCard.getExpirationDate(),result.getExpirationDate());
    }

    @Test
    void convertToDebitCardResponseDto(){
        DebitCard debitCard = new DebitCard();
        debitCard.setId(1);
        debitCard.setCardNumber("1111");
        debitCard.setCardholderName("albi");
        debitCard.setExpirationDate("06/25");

        DebitCardResponseDto debitCardResponseDto = new DebitCardResponseDto();
        debitCardResponseDto.setCardNumber("1111");
        debitCardResponseDto.setCardHolderName("albi");
        debitCardResponseDto.setExpirationDate("06/25");
        debitCardResponseDto.setApproved(Boolean.TRUE);
        debitCard.setDisapproveReason(null);


        var result = debitCardConverter.convertToDebitCardResponseDto(debitCard);
        Assertions.assertEquals(debitCardResponseDto.getCardNumber(),result.getCardNumber());
        Assertions.assertEquals(debitCardResponseDto.getCardNumber(),result.getCardNumber());
        Assertions.assertEquals(debitCardResponseDto.getCardNumber(),result.getCardNumber());




    }
}