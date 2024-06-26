package com.example.BankingApp.account.controller;

import com.example.BankingApp.account.dto.NewAccountDto;
import com.example.BankingApp.account.service.AccountService;
import com.example.BankingApp.user.config.ApiPaths;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping(ApiPaths.ACCOUNT)
public class AccountController {
    Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping(ApiPaths.REQUEST)
    public ResponseEntity<Integer>addAccount(@RequestBody NewAccountDto newAccountDto){
        logger.info("attempt to add new account {}",newAccountDto);
        var result = accountService.addAccount(newAccountDto);
        logger.info("account with id {} is added ",result);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('BANKER')")
    @PutMapping(ApiPaths.APPROVED)
    public ResponseEntity<Void>updateAccount(@RequestParam Integer id, @RequestParam Boolean approved){
        logger.info("attempt to approved account with id: {}",id);
        accountService.update(id,approved);
        logger.info("account with id: {} is approved", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
