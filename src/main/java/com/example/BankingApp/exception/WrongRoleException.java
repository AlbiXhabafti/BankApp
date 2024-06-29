package com.example.BankingApp.exception;

public class WrongRoleException extends RuntimeException{
    public WrongRoleException(String message){
        super(message);
    }
}
