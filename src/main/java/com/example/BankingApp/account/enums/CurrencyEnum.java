package com.example.BankingApp.account.enums;

import com.example.BankingApp.user.enums.RoleEnum;

import java.util.Arrays;

public enum CurrencyEnum {
    EUR("Euro");

    private final String value;
    public String getValue(){
        return value;
    }
    CurrencyEnum(String value) {
        this.value = value;
    }
    public static CurrencyEnum fromValue(String value){
        return Arrays.stream(values())
                .filter(s-> value.equals(s.getValue()))
                .findAny().orElse(null);
    }
}
