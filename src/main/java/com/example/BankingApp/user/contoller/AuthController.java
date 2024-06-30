package com.example.BankingApp.user.contoller;

import com.example.BankingApp.user.dto.JwtAuthResponse;
import com.example.BankingApp.user.dto.LoginDto;
import com.example.BankingApp.user.service.AuthService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        logger.info("attempting to logIn with email: {}",loginDto.getEmail());
        var result =  authService.login(loginDto);
        logger.info(" user with email {} is successfully logIn",loginDto.getEmail());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam String email,@RequestParam String token){
        logger.info("attempting to logout with email: {}",email);
        authService.logout(email,token);
        logger.info(" user with logout {} is successfully logIn",email);
        return new ResponseEntity<> (HttpStatus.OK);
    }
}