package com.example.security.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(){
        super("duplicated email ");
    }

    public EmailAlreadyExistsException(String message){
        super(message);
    }
}
