package com.example.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHendlerExcepion {
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<RestErrorMessage> buildResponse(EmailAlreadyExistsException ex){
        RestErrorMessage threat = new RestErrorMessage(HttpStatus.CONFLICT, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threat);
    }
    @ExceptionHandler(CodeNotFoundException.class)
    public ResponseEntity<RestErrorMessage> codeNotFound(CodeNotFoundException ex){
        RestErrorMessage c = new RestErrorMessage(HttpStatus.NOT_FOUND , ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(c);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<RestErrorMessage> userNotFound(UserNotFoundException ex){
        RestErrorMessage k = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(k);
    }
}
