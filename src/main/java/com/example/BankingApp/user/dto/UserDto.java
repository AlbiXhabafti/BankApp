package com.example.BankingApp.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String name;
    private String username;
    private String email;
    private String password;
    private String role;
}
