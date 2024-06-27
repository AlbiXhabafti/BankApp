package com.example.BankingApp.exception;

public class NoResultFoundException extends RuntimeException{
    public NoResultFoundException(String message){
        super(message);
    }
}
