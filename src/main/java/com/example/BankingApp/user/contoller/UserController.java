package com.example.BankingApp.user.contoller;

import com.example.BankingApp.user.config.ApiPaths;
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

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(ApiPaths.BANKER)
    public ResponseEntity<Integer>addBanker(@RequestBody UserRequestDto userRequestDto){
        logger.info("trying to add new banker {}", userRequestDto);
        var result = userService.addBanker(userRequestDto);
        logger.info("new banker with id {} is added",result);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(ApiPaths.BANKER)
    public ResponseEntity<Integer>updateBanker(@RequestParam Integer id ,@RequestBody UserDto userDto){
        logger.info("trying to update banker {}", userDto);
        var result = userService.updateBanker(id,userDto);
        logger.info(" banker with id {} is updated",result);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(ApiPaths.BANKER)
    public ResponseEntity<Void>deleteBanker(@RequestParam Integer id){
        logger.info("trying to delete  banker {}", id);
        userService.deleteBanker(id);
        logger.info(" banker with id {} is deleted",id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('BANKER')")
    @PostMapping(ApiPaths.CLIENT)
    public ResponseEntity<Integer>addClient(@RequestBody UserRequestDto userRequestDto){
        logger.info("trying to add new client {}", userRequestDto);
        var result = userService.addClient(userRequestDto);
        logger.info("new client with id {} is added",result);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('BANKER')")
    @PutMapping(ApiPaths.CLIENT)
    public ResponseEntity<Integer>updateClient(@RequestParam Integer id ,@RequestBody UserDto userDto){
        logger.info("trying to update client {}", userDto);
        var result = userService.updateClient(id,userDto);
        logger.info(" client with id {} is updated",result);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('BANKER')")
    @DeleteMapping(ApiPaths.CLIENT)
    public ResponseEntity<Void>deleteClient(@RequestParam Integer id){
        logger.info("trying to delete  client {}", id);
        userService.deleteClient(id);
        logger.info(" client with id {} is deleted",id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
