package com.example.BankingApp.user.enums;

import java.util.Arrays;

public enum RoleEnum {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_BANKER("ROLE_BANKER"),
    ROLE_CLIENT("ROLE_CLIENT");
    private final String value;

    public String getValue() {
        return value;
    }
    RoleEnum(String value) {
        this.value = value;
    }
    public static RoleEnum fromValue(String value){
        return Arrays.stream(values())
                .filter(s-> value.equals(s.getValue()))
                .findAny().orElse(null);
    }
}
