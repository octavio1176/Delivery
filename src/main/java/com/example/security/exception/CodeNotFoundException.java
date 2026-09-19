package com.example.security.exception;

public class CodeNotFoundException extends RuntimeException{
    public CodeNotFoundException(String message){
        super(message);
    }

    public CodeNotFoundException(){
         super("code not found ");
    }

}
