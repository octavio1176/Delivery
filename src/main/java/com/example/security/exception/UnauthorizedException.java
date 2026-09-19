package com.example.security.exception;

public class UnauthorizedException extends  RuntimeException{
    public UnauthorizedException(){
        super("user not allowed");
    }
}
