package com.example.BankingApp.card.service;

import com.example.BankingApp.card.dto.NewDebitCardDto;

public interface DebitCardService {
    Integer add(NewDebitCardDto dto);
    void update(Integer id, Boolean approved,String disapproveReason);
}
