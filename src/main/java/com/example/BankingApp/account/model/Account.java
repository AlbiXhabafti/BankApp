package com.example.BankingApp.account.model;

import com.example.BankingApp.transaction.model.Transaction;
import com.example.BankingApp.account.enums.CurrencyEnum;
import com.example.BankingApp.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String iban;

    @Enumerated(EnumType.STRING)
    private CurrencyEnum currencyEnum;

    private Double balance;


    private boolean approved;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private User modifiedBy;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transaction> transactions = new HashSet<>();


}
