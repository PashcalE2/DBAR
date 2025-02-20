package com.main.auth.exeptions;

import org.springframework.http.HttpStatus;

public class WrongParamsException extends AuthException {

    public WrongParamsException(String message, HttpStatus status) {
        super(message, status);
    }

}
