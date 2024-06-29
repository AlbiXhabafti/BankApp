package com.example.BankingApp.user.service.impl;

import com.example.BankingApp.user.converter.UserConverter;
import com.example.BankingApp.user.dto.UserRequestDto;
import com.example.BankingApp.user.enums.RoleEnum;
import com.example.BankingApp.user.model.Role;
import com.example.BankingApp.user.model.User;
import com.example.BankingApp.user.repository.RoleRepository;
import com.example.BankingApp.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private  UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserConverter userConverter;

    @Test
    void addClient() {
        UserRequestDto dto = new UserRequestDto();
        dto.setFirstName("albi");
        dto.setLastName("xhabafti");
        dto.setEmail("albi@gmail.com");
        dto.setPassword("password");

        Role role = new Role();
        role.setId(1);
        role.setRoleEnum(RoleEnum.ROLE_BANKER);
        User user = new User();
        user.setId(1);
        user.setFirstName("albi");
        user.setLastName("xhabafti");
        user.setPassword("albi@gmail.com");
        user.setRoles(Collections.singleton(role));




    }
}