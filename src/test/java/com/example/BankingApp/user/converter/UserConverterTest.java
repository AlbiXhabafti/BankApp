package com.example.BankingApp.user.converter;

import com.example.BankingApp.user.dto.UserDto;
import com.example.BankingApp.user.dto.UserRequestDto;
import com.example.BankingApp.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserConverterTest {

    @InjectMocks
    private UserConverter userConverter;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void convertToUser(){

        UserRequestDto dto = new UserRequestDto();
        dto.setFirstName("albi");
        dto.setLastName("xhabafti");
        dto.setEmail("albi.gmail.com");
        dto.setPassword("12234");
        dto.setEmail(dto.getEmail());

        User expect = new User();
        expect.setFirstName("albi");
        expect.setLastName("xhabafti");
        expect.setEmail("albi.gmail.com");
        expect.setPassword("encryptPass");

        Mockito.when(passwordEncoder.encode(dto.getPassword())).thenReturn("encryptPass");

        User result = userConverter.convertToUser(dto);

        Assertions.assertEquals(expect.getEmail(),result.getEmail());
        Assertions.assertEquals(expect.getLastName(),result.getLastName());
        Assertions.assertEquals(expect.getFirstName(),result.getFirstName());
        Assertions.assertEquals(expect.getPassword(),result.getPassword());
    }
    @Test
    void convertToUser_when_try_to_update(){
        UserDto dto = new UserDto();
        dto.setFirstName("albi");
        dto.setLastName("xhabafti");
        dto.setEmail("albi.gmail.com");
        dto.setPassword("12234");
        dto.setEmail(dto.getEmail());

        User existingUser = new User();
        existingUser.setFirstName("test");
        existingUser.setLastName("lastName");
        existingUser.setEmail("testgmail.com");
        existingUser.setPassword("password");

        User expect = new User();
        expect.setFirstName("albi");
        expect.setLastName("xhabafti");
        expect.setEmail("albi.gmail.com");
        expect.setPassword("encryptPass");
        Mockito.when(passwordEncoder.encode(dto.getPassword())).thenReturn("encryptPass");

        User result = userConverter.convertToUser(existingUser,dto);

        Assertions.assertEquals(expect.getEmail(),result.getEmail());
        Assertions.assertEquals(expect.getLastName(),result.getLastName());
        Assertions.assertEquals(expect.getFirstName(),result.getFirstName());
        Assertions.assertEquals(expect.getPassword(),result.getPassword());
    }
}