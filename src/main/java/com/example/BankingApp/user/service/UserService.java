package com.example.BankingApp.user.service;

import com.example.BankingApp.user.dto.UserDto;
import com.example.BankingApp.user.dto.UserRequestDto;

public interface UserService {
    Integer addBanker(UserRequestDto userRequestDto);
    Integer addClient(UserRequestDto userRequestDto);
    Integer updateBanker(Integer id, UserDto userDto);
    void deleteBanker(Integer id);
    Integer updateClient(Integer id, UserDto userDto);
    void deleteClient(Integer id);

}
