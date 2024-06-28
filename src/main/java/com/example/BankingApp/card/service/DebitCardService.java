package com.example.BankingApp.card.service;

import com.example.BankingApp.card.dto.DebitCardResponseDto;
import com.example.BankingApp.card.dto.NewDebitCardDto;

import java.util.List;

public interface DebitCardService {
    Integer add(NewDebitCardDto dto, String email);
    void update(Integer id, Boolean approved,String disapproveReason,String email);
    List<DebitCardResponseDto> get(String email);
}
