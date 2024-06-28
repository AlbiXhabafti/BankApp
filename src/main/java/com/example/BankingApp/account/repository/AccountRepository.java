package com.example.BankingApp.account.repository;

import com.example.BankingApp.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Integer> {

    Optional<Account> findByIbanAndCreatedByEmail(String iban, String email);

    @Query("SELECT a FROM Account a INNER JOIN User u ON  a.createdBy.id = u.id WHERE u.email = :email ")
    List<Account> findAccountByEmail(String email);

    Optional<Account>findByIban(String iban);


}
