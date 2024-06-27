package com.example.BankingApp.account.controller;

import com.example.BankingApp.account.dto.AccountDto;
import com.example.BankingApp.account.dto.AccountResponseDto;
import com.example.BankingApp.account.service.AccountService;
import com.example.BankingApp.user.config.ApiPaths;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
    public ResponseEntity<Integer>addAccount(@RequestBody AccountDto accountDto, Principal principal){
        logger.info("attempt to add new account {}", accountDto);
        var result = accountService.add(accountDto,principal.getName());
        logger.info("account with id {} is added ",result);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('BANKER')")
    @PutMapping
    public ResponseEntity<Void>updateAccount( @RequestParam Boolean approved,@RequestParam Integer id){
        logger.info("attempt to approved account with id: {}",id);
        accountService.update(id,approved);
        logger.info("account with id: {} is approved", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping
    public ResponseEntity<List<AccountResponseDto>>get(Principal principal){
        logger.info("attempt to get account list for user: {} ",principal.getName());
        var result = accountService.get(principal.getName());
        logger.info("getting account list {}",result);
        return new ResponseEntity<>(result,HttpStatus.OK);

    }
    @PreAuthorize("hasRole('BANKER')")
    @GetMapping(ApiPaths.EMAIL)
    public ResponseEntity<List<AccountResponseDto>>getAccount(@PathVariable String email){
        logger.info("attempt to get account list for user: {} ",email);
        var result = accountService.get(email);
        logger.info("getting account list {}",result);
        return new ResponseEntity<>(result,HttpStatus.OK);

    }


}