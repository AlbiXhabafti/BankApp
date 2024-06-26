package com.example.BankingApp.card.repository;

import com.example.BankingApp.card.model.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebitCardRepository extends JpaRepository<DebitCard,Integer> {
}
