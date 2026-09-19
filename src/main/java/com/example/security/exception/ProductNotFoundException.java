package com.example.security.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message){
        super(message);
    }
    public ProductNotFoundException(){
        super("product not found ");
    }
}
