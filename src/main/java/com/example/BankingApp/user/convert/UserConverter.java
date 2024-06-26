package com.example.BankingApp.user.convert;

import com.example.BankingApp.user.dto.UserRequestDto;
import com.example.BankingApp.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User convertToUser(UserRequestDto dto){
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        return user;
    }
}
