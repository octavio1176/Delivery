package com.example.security.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<RestErrorMessage> buildResponse(EmailAlreadyExistsException ex) {
        RestErrorMessage threat = new RestErrorMessage(HttpStatus.CONFLICT, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threat);
    }

    @ExceptionHandler(CodeNotFoundException.class)
    public ResponseEntity<RestErrorMessage> codeNotFound(CodeNotFoundException ex) {
        RestErrorMessage c = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(c);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<RestErrorMessage> userNotFound(UserNotFoundException ex) {
        RestErrorMessage k = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(k);
    }

    @ExceptionHandler(InvalidCodeException.class)
    public ResponseEntity<RestErrorMessage> invalidCode(InvalidCodeException  ex) {
        RestErrorMessage l = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(l);
    }

    @ExceptionHandler(InsufficientStock.class)
    public ResponseEntity<RestErrorMessage> insufficientStock(InsufficientStock ex) {
        RestErrorMessage p = new RestErrorMessage(HttpStatus.INSUFFICIENT_STORAGE, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body(p);
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<RestErrorMessage> productNotFound(ProductNotFoundException ex){
        RestErrorMessage r = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(r);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<RestErrorMessage> userNotAllowed(UnauthorizedException e){
        RestErrorMessage m =  new RestErrorMessage(HttpStatus.UNAUTHORIZED, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(m);

    }


}



