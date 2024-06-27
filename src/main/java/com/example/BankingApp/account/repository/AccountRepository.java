package com.example.BankingApp.account.repository;

import com.example.BankingApp.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    Account findByIban(String iban);
    @Query("SELECT a FROM Account a INNER JOIN User u ON  a.createdBy.id = u.id WHERE u.email = :email ")
    List<Account> findAccountByEmail(String email);
}
