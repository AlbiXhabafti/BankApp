package com.example.BankingApp.user.contoller;

import com.example.BankingApp.user.dto.LoginDto;
import com.example.BankingApp.user.dto.UserResponseDto;
import com.example.BankingApp.utils.ApiPaths;
import com.example.BankingApp.user.dto.UserDto;
import com.example.BankingApp.user.dto.UserRequestDto;
import com.example.BankingApp.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping(ApiPaths.USER)
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(ApiPaths.LOGIN)
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginDto loginDto){
        logger.info("attempt to logIn with email: {}",loginDto.getEmail());
        var result =  userService.login(loginDto);
        logger.info(" user with email {} is successfully logIn",loginDto.getEmail());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping(ApiPaths.LOGOUT)
    public ResponseEntity<Void> logout(@RequestParam String email,@RequestParam String token){
        logger.info("attempting to logout with email: {} and token {}",email,token);
        userService.logout(email,token);
        logger.info(" user with logout {} is successfully logIn",email);
        return new ResponseEntity<> (HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(ApiPaths.BANKER)
    public ResponseEntity<Void>addBanker(@RequestBody UserRequestDto userRequestDto){
        logger.info("attempt to add new banker {}", userRequestDto);
        userService.addBanker(userRequestDto);
        logger.info("new banker with email {} is added",userRequestDto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(ApiPaths.BANKER)
    public ResponseEntity<Void>updateBanker(@RequestParam String email ,@RequestBody UserDto userDto){
        logger.info("attempt to update banker {}", userDto);
        userService.updateBanker(email,userDto);
        logger.info(" banker with email {} is updated",email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(ApiPaths.BANKER)
    public ResponseEntity<Void>deleteBanker(@RequestParam String email){
        logger.info("attempt to delete  banker with email {}", email);
        userService.deleteBanker(email);
        logger.info(" banker with email {} is deleted",email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('BANKER')")
    @PostMapping(ApiPaths.CLIENT)
    public ResponseEntity<Void>addClient(@RequestBody UserRequestDto userRequestDto){
        logger.info("attempt to add new client {}", userRequestDto);
        userService.addClient(userRequestDto);
        logger.info("new client with email {} is added",userRequestDto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('BANKER')")
    @PutMapping(ApiPaths.CLIENT)
    public ResponseEntity<Void>updateClient(@RequestParam String email ,@RequestBody UserDto userDto){
        logger.info("attempt to update client {}", userDto);
         userService.updateClient(email,userDto);
        logger.info(" client with email {} is updated",email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('BANKER')")
    @DeleteMapping(ApiPaths.CLIENT)
    public ResponseEntity<Void>deleteClient(@RequestParam String email){
        logger.info("attempt to delete  client {}", email);
        userService.deleteClient(email);
        logger.info(" client with email {} is deleted",email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
