package com.example.BankingApp.transaction.repository;

import com.example.BankingApp.transaction.dto.TransactionResponseDto;
import com.example.BankingApp.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,Integer>{
    @Query("SELECT t FROM Transaction t  " +
            "INNER JOIN User u ON t.createdBy.id = u.id WHERE u.email = :email")
    List<Transaction>findTransactionByEmail(String email);

    List<Transaction>findByCreatedByEmail(String email);


}
