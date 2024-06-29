package com.example.BankingApp.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private String firstName;

    private String lastName;

    @NotNull
    @NotNull
    private String email;

    @NotNull
    private String password;

}
