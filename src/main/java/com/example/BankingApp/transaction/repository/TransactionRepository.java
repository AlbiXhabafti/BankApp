package com.example.BankingApp.transaction.repository;

import com.example.BankingApp.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Integer>{
}
