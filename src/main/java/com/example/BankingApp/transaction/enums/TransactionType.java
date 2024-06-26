package com.example.BankingApp.transaction.enums;

import com.example.BankingApp.account.enums.CurrencyEnum;

import java.util.Arrays;

public enum TransactionType {
    DEBIT("DEBIT"),
    CREDIT("CREDIT");
    private final String value;

    TransactionType(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
    public static TransactionType fromValue(String value){
        return Arrays.stream(values())
                .filter(s-> value.equals(s.getValue()))
                .findAny().orElse(null);
    }
}
