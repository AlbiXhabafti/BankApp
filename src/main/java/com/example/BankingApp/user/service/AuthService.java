package com.example.BankingApp.user.service;

import com.example.BankingApp.user.dto.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}