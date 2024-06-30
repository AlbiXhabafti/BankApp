package com.example.BankingApp.transaction.controller;

import com.example.BankingApp.transaction.dto.TransactionDto;
import com.example.BankingApp.transaction.dto.TransactionResponseDto;
import com.example.BankingApp.transaction.service.TransactionService;
import com.example.BankingApp.user.enums.ApiPaths;
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
@RequestMapping(ApiPaths.TRANSACTION)
public class TransactionController {
    Logger logger = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping
    public ResponseEntity<Void>add(@RequestBody TransactionDto transactionDto, Principal principal){
        logger.info("attempt to add new transaction {}",transactionDto);
        transactionService.add(transactionDto,principal.getName());
        logger.info("new transaction  is created");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping
    public ResponseEntity<List<TransactionResponseDto>>get(Principal principal){
        logger.info("attempt to get  transaction for logged user:{}",principal.getName());
        var result = transactionService.get(principal.getName());
        logger.info("getting list of  transaction  {}",result);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('BANKER')")
    @GetMapping(ApiPaths.USER)
    public ResponseEntity<List<TransactionResponseDto>>get(@RequestParam String email){
        logger.info("attempt to get  transaction for  user:{}",email);
        var result = transactionService.get(email);
        logger.info(" getting transactions {}",result);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

}
