package com.example.BankingApp.card.repository;

import com.example.BankingApp.card.model.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DebitCardRepository extends JpaRepository<DebitCard,Integer> {

    List<DebitCard> getByCreatedByEmail(String email);
}
