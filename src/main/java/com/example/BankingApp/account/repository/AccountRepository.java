package com.example.BankingApp.account.repository;

import com.example.BankingApp.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    Account findByIban(String iban);
}
