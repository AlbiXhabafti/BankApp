package com.example.BankingApp.user.service.impl;

import com.example.BankingApp.exception.NoResultFoundException;
import com.example.BankingApp.user.dto.JwtAuthResponse;
import com.example.BankingApp.user.dto.LoginDto;
import com.example.BankingApp.user.jwt.JwtTokenProvider;
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
    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
        User user = userRepository.findByEmailAndDeletedFalse(loginDto.getEmail()).orElseThrow(()-> new NoResultFoundException("User not found or deleted."));
        if (user == null) {
            throw new RuntimeException("User not found or deleted.");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return jwtAuthResponse;
    }

    @Override
    public void logout(LoginDto loginDto) {
        User user = userRepository.findByEmailAndDeletedFalse(loginDto.getEmail()).orElseThrow(()-> new NoResultFoundException("User not found or deleted."));
        if (user == null) {
            throw new RuntimeException("User not found or deleted.");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(null);

    }
}