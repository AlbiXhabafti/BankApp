package com.example.BankingApp.user.converter;

import com.example.BankingApp.user.dto.UserDto;
import com.example.BankingApp.user.dto.UserRequestDto;
import com.example.BankingApp.user.enums.RoleEnum;
import com.example.BankingApp.user.model.Role;
import com.example.BankingApp.user.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserConverter {
    private final PasswordEncoder passwordEncoder;

    public UserConverter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User convertToUser(UserRequestDto dto){
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        return user;
    }

    public User convertToUser(User user, UserDto dto){
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (Objects.nonNull(dto.getPassword())) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (Objects.nonNull(dto.getFirstName())){
            user.setFirstName(dto.getFirstName());
        }
        if (Objects.nonNull(dto.getLastName())){
            user.setLastName(dto.getLastName());
        }
        return user;
    }
}
