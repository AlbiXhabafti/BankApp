package com.example.BankingApp.user.service;

import com.example.BankingApp.user.dto.JwtAuthResponse;
import com.example.BankingApp.user.dto.LoginDto;

public interface AuthService {
    JwtAuthResponse login(LoginDto loginDto);
    void logout(String email,String token);
}