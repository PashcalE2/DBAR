package com.main.auth.exeptions;

import org.springframework.http.HttpStatus;

public class BadCredentialsException extends AuthException {

    public BadCredentialsException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

}
