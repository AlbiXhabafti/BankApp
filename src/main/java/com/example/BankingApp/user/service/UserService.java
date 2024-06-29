package com.example.BankingApp.user.service;

import com.example.BankingApp.user.dto.UserDto;
import com.example.BankingApp.user.dto.UserRequestDto;

public interface UserService {
    void addBanker(UserRequestDto userRequestDto);
    void updateBanker(String email, UserDto userDto);
    void deleteBanker(String email);

    void addClient(UserRequestDto userRequestDto);
    void updateClient(String email, UserDto userDto);
    void deleteClient(String email);

}
