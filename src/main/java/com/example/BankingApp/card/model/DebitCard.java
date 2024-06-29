package com.example.BankingApp.card.model;

import com.example.BankingApp.account.model.Account;
import com.example.BankingApp.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class DebitCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String cardNumber;

    private String cardholderName;

    private String expirationDate;

    @OneToOne(cascade = CascadeType.ALL)
    private Account account;

    private boolean approved;

    private String disapproveReason;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private User modifiedBy;

}
