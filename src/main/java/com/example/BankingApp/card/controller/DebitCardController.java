package com.example.BankingApp.card.controller;

import com.example.BankingApp.card.dto.NewDebitCardDto;
import com.example.BankingApp.card.service.DebitCardService;
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
@RequestMapping(ApiPaths.DEBIT_CARD)
public class DebitCardController {
    Logger logger = LoggerFactory.getLogger(DebitCardController.class);
    private final DebitCardService debitCardService;

    public DebitCardController(DebitCardService debitCardService) {
        this.debitCardService = debitCardService;
    }
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping(ApiPaths.REQUEST)
    public ResponseEntity<Integer>add(@RequestBody NewDebitCardDto dto){
        logger.info("attempt to add new card {}",dto);
        var result = debitCardService.add(dto);
        logger.info("new card with id: {} is added",result);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('BANKER')")
    @PutMapping(ApiPaths.APPROVED)
    public ResponseEntity<Void>update(@RequestParam Integer id, @RequestParam Boolean approved, @RequestParam String disapproveReason){
        logger.info("attempt to approved debit card with id: {}",id);
        debitCardService.update(id,approved,disapproveReason);
        logger.info("debit card with id: {} is approved {}",id,approved);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
