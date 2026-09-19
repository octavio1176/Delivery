package com.example.security.exception;

public class InsufficientStock  extends RuntimeException{
    public InsufficientStock(String message){
        super(message);
    }

    public InsufficientStock(){
        super("insufficient stock ");
    }

}
