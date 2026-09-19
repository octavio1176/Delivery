package com.example.security.exception;

public class InvalidCodeException extends  RuntimeException {
    public InvalidCodeException(String message){
        super(message);
    }
    public InvalidCodeException(){
        super("Code not Found ");
    }

}
