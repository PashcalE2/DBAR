package main.isbd.exception;

import org.springframework.http.HttpStatus;

public class BadCredentialsException extends BaseAppException {
    public BadCredentialsException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
