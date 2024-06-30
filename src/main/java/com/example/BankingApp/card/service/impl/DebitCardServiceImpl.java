package com.example.BankingApp.card.service.impl;

import com.example.BankingApp.account.model.Account;
import com.example.BankingApp.account.repository.AccountRepository;
import com.example.BankingApp.card.converter.DebitCardConverter;
import com.example.BankingApp.card.dto.DebitCardResponseDto;
import com.example.BankingApp.card.dto.NewDebitCardDto;
import com.example.BankingApp.card.model.DebitCard;
import com.example.BankingApp.card.repository.DebitCardRepository;
import com.example.BankingApp.card.service.DebitCardService;
import com.example.BankingApp.exception.NoResultFoundException;
import com.example.BankingApp.user.model.User;
import com.example.BankingApp.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DebitCardServiceImpl implements DebitCardService {
    private final DebitCardRepository debitCardRepository;
    private final DebitCardConverter debitCardConverter;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;


    public DebitCardServiceImpl(DebitCardRepository debitCardRepository, DebitCardConverter debitCardConverter, AccountRepository accountRepository, UserRepository userRepository) {
        this.debitCardRepository = debitCardRepository;
        this.debitCardConverter = debitCardConverter;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void  add(NewDebitCardDto dto, String email) {
        if (dto.getSalary() <= 500) {
            throw new IllegalArgumentException("Salary must be greater than 500");
        }
        Account account = accountRepository.findByIbanAndCreatedByEmail(dto.getIban(),email)
                .orElseThrow(()-> new NoResultFoundException("Please verify your iban. Try again."));
        if (!account.isApproved()){
            throw new NullPointerException("account is not approved");
        }
        DebitCard debitCard = debitCardConverter.convertToDebitCard(dto);
        debitCard.setAccount(account);
        debitCard.setCreatedBy(account.getCreatedBy());
        debitCardRepository.save(debitCard);
    }

    @Override
    public void update(Integer id, Boolean approved,String disapproveReason,String email) {
        DebitCard debitCard = debitCardRepository.findById(id)
                .orElseThrow(()->new NoResultFoundException("debit card is not found"));
        debitCard.setApproved(approved);
        if (approved.equals(false)){
            debitCard.setDisapproveReason(disapproveReason);
        }else {
            debitCard.setDisapproveReason(null);
        }
        User user = userRepository.findByEmailAndDeletedFalse(email).orElse(null);
        debitCard.setModifiedBy(user);
        debitCardRepository.save(debitCard);
    }

    @Override
    public List<DebitCardResponseDto> get(String email) {
        List<DebitCard>cardList = debitCardRepository.getByCreatedByEmail(email);
        return cardList.stream().map(debitCardConverter::convertToDebitCardResponseDto).toList();
    }
}
