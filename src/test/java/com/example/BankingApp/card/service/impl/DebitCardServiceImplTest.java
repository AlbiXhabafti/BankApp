package com.example.BankingApp.card.service.impl;

import com.example.BankingApp.account.model.Account;
import com.example.BankingApp.account.repository.AccountRepository;
import com.example.BankingApp.card.converter.DebitCardConverter;
import com.example.BankingApp.card.dto.DebitCardResponseDto;
import com.example.BankingApp.card.dto.NewDebitCardDto;
import com.example.BankingApp.card.model.DebitCard;
import com.example.BankingApp.card.repository.DebitCardRepository;
import com.example.BankingApp.exception.NoResultFoundException;
import com.example.BankingApp.user.model.User;
import com.example.BankingApp.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DebitCardServiceImplTest {
    @Mock
    DebitCardRepository debitCardRepository;

    @Mock
    DebitCardConverter debitCardConverter;

    @Mock
    AccountRepository accountRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    DebitCardServiceImpl debitCardService;

    @Test
    void add(){
        String email = "albi@gmail.com";

        User user = new User();
        user.setId(1);
        user.setEmail(email);

        Account account = new Account();
        account.setId(1);
        account.setIban("1234");
        account.setBalance(700.8);
        account.setCreatedBy(user);
        account.setApproved(Boolean.TRUE);

        NewDebitCardDto dto = new NewDebitCardDto();
        dto.setCardNumber("1111");
        dto.setIban("1234");
        dto.setSalary(1000.0);
        dto.setCardholderName("albi");
        dto.setExpirationDate("06/25");

        DebitCard debitCard = new DebitCard();
        debitCard.setId(1);
        debitCard.setCardNumber("1111");
        debitCard.setCardholderName("albi");
        debitCard.setExpirationDate("06/25");
        debitCard.setAccount(account);
        debitCard.setCreatedBy(user);


        Mockito.when(accountRepository.findByIbanAndCreatedByEmail(dto.getIban(),email)).thenReturn(Optional.of(account));
        Mockito.when(debitCardConverter.convertToDebitCard(dto)).thenReturn(debitCard);
        Mockito.when(debitCardRepository.save(debitCard)).thenReturn(debitCard);

        debitCardService.add(dto,email);

        verify(debitCardRepository, Mockito.times(1)).save(debitCard);
    }
    @Test
    void add_user_not_found() {
        String email = "albi@gmail.com";

        NewDebitCardDto dto = new NewDebitCardDto();
        dto.setCardNumber("1111");
        dto.setIban("1234");
        dto.setSalary(1000.0);
        dto.setCardholderName("albi");
        dto.setExpirationDate("06/25");

        assertThrows(NoResultFoundException.class, () -> debitCardService.add(dto, email));

    }
    @Test
    void add_when_account_is_not_approved() {
        String email = "albi@gmail.com";

        NewDebitCardDto dto = new NewDebitCardDto();
        dto.setCardNumber("1111");
        dto.setIban("1234");
        dto.setSalary(1000.0);
        dto.setCardholderName("albi");
        dto.setExpirationDate("06/25");

        Account account = new Account();
        account.setId(1);
        account.setIban("1234");
        account.setBalance(700.8);
        account.setApproved(Boolean.FALSE);

        Mockito.when(accountRepository.findByIbanAndCreatedByEmail(dto.getIban(),email)).thenReturn(Optional.of(account));
        assertThrows(NullPointerException.class, () -> debitCardService.add(dto, email));

    }
    @Test
    void update(){
        Integer id=1;
        Boolean approved = Boolean.TRUE;
        String email = "albi@gmail.com";

        User user = new User();
        user.setId(1);
        user.setEmail(email);

        Account account = new Account();
        account.setId(1);
        account.setIban("1234");
        account.setBalance(700.8);
        account.setCreatedBy(user);
        account.setApproved(Boolean.TRUE);

        DebitCard debitCard = new DebitCard();
        debitCard.setId(1);
        debitCard.setCardNumber("1111");
        debitCard.setCardholderName("albi");
        debitCard.setExpirationDate("06/25");
        debitCard.setAccount(account);
        debitCard.setCreatedBy(user);

        Mockito.when(debitCardRepository.findById(id)).thenReturn(Optional.of(debitCard));
        Mockito.when(userRepository.findByEmailAndDeletedFalse(email)).thenReturn(Optional.of(user));
        Mockito.when(debitCardRepository.save(any(DebitCard.class))).thenReturn(debitCard);

        debitCardService.update(id,approved,null,email);
        verify(debitCardRepository, Mockito.times(1)).save(debitCard);
    }

    @Test
    void update_throws_exception(){
        Integer id=1;
        Boolean approved = Boolean.TRUE;
        String email = "albi@gmail.com";

        assertThrows(NoResultFoundException.class,()->debitCardService.update(id,approved,"",email));
    }
    @Test
    void get(){
        String email = "albi@gmail.com";


        User user = new User();
        user.setId(1);
        user.setEmail(email);

        Account account = new Account();
        account.setId(1);
        account.setIban("1234");
        account.setBalance(700.8);
        account.setCreatedBy(user);
        account.setApproved(Boolean.TRUE);

        DebitCard debitCard = new DebitCard();
        debitCard.setId(1);
        debitCard.setCardNumber("1111");
        debitCard.setCardholderName("albi");
        debitCard.setExpirationDate("06/25");
        debitCard.setAccount(account);
        debitCard.setCreatedBy(user);

        DebitCardResponseDto debitCardResponseDto = new DebitCardResponseDto();
        debitCardResponseDto.setCardNumber("1111");
        debitCardResponseDto.setCardHolderName("albi");
        debitCardResponseDto.setExpirationDate("06/25");
        debitCardResponseDto.setApproved(Boolean.TRUE);
        debitCard.setDisapproveReason(null);

        Mockito.when(debitCardRepository.getByCreatedByEmail(email)).thenReturn(List.of(debitCard));
        Mockito.when(debitCardConverter.convertToDebitCardResponseDto(debitCard)).thenReturn(debitCardResponseDto);

        var response = debitCardService.get(email);
        Assertions.assertEquals(debitCardResponseDto.getCardNumber(), response.get(0).getCardNumber());
        Assertions.assertEquals(debitCardResponseDto.getCardHolderName(), response.get(0).getCardHolderName());
        Assertions.assertEquals(debitCardResponseDto.getApproved(), response.get(0).getApproved());


    }







}