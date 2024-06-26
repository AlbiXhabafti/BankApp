package com.example.BankingApp.card.service.impl;

import com.example.BankingApp.account.model.Account;
import com.example.BankingApp.account.repository.AccountRepository;
import com.example.BankingApp.card.converter.DebitCardConverter;
import com.example.BankingApp.card.dto.NewDebitCardDto;
import com.example.BankingApp.card.model.DebitCard;
import com.example.BankingApp.card.repository.DebitCardRepository;
import com.example.BankingApp.card.service.DebitCardService;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Service;

@Service
public class DebitCardServiceImpl implements DebitCardService {
    private final DebitCardRepository debitCardRepository;
    private final DebitCardConverter debitCardConverter;
    private final AccountRepository accountRepository;


    public DebitCardServiceImpl(DebitCardRepository debitCardRepository, DebitCardConverter debitCardConverter, AccountRepository accountRepository) {
        this.debitCardRepository = debitCardRepository;
        this.debitCardConverter = debitCardConverter;
        this.accountRepository = accountRepository;
    }

    @Override
    public Integer add(NewDebitCardDto dto) {
        if (dto.getSalary() <= 500) {
            throw new IllegalArgumentException("Salary must be greater than 500");
        }
            DebitCard debitCard = debitCardConverter.convertToDebitCard(dto);
            Account account = accountRepository.findByIban(dto.getIban());
            debitCard.setAccount(account);
            debitCardRepository.save(debitCard);
            return debitCard.getId();

    }

    @Override
    public void update(Integer id, Boolean approved,String disapproveReason) {
        DebitCard debitCard = debitCardRepository.findById(id)
                .orElseThrow(()->new NoResultException("debit card is not found"));

        debitCard.setApproved(approved);
        if (approved.equals(false)){
            debitCard.setDisapproveReason(disapproveReason);
        }else {
            debitCard.setDisapproveReason(null);
        }
        debitCardRepository.save(debitCard);
    }
}
