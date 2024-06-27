package com.example.BankingApp.card.repository;

import com.example.BankingApp.card.model.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DebitCardRepository extends JpaRepository<DebitCard,Integer> {
    @Query("SELECT d FROM DebitCard d INNER JOIN " +
            "Account a ON d.account.id = a.id " +
            "INNER JOIN User u ON a.createdBy.id = u.id " +
            "WHERE u.email = :email")
    List<DebitCard> getByEmail(String email);

}
