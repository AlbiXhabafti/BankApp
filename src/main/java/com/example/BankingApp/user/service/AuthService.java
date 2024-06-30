package com.example.BankingApp.user.service;

import com.example.BankingApp.user.dto.UserResponseDto;
import com.example.BankingApp.user.dto.LoginDto;

public interface AuthService {
    UserResponseDto login(LoginDto loginDto);
    void logout(String email,String token);
}