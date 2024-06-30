package com.example.BankingApp.user.service.impl;

import com.example.BankingApp.exception.NoResultFoundException;
import com.example.BankingApp.user.dto.UserResponseDto;
import com.example.BankingApp.user.dto.LoginDto;
import com.example.BankingApp.user.model.User;
import com.example.BankingApp.user.repository.UserRepository;
import com.example.BankingApp.user.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private UserRepository userRepository;

    @Override
    public UserResponseDto login(LoginDto loginDto) {
        User user = userRepository.findByEmailAndDeletedFalse(loginDto.getEmail()).orElseThrow(()-> new NoResultFoundException("User not found or deleted."));
        if (user == null) {
            throw new RuntimeException("User not found or deleted.");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);


        String token = tokenService.generateToken(authentication);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setAccessToken(token);
        return userResponseDto;
    }


    @Override
    public void logout(String email,String token) {
        User user = userRepository.findByEmailAndDeletedFalse(email).orElseThrow(()-> new NoResultFoundException("User not found or deleted."));
        if (user == null) {
            throw new RuntimeException("User not found or deleted.");
        }
        tokenService.revokeToken(email,token);

    }
}