package com.main.auth.exeptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TokenException extends RuntimeException {

    private final HttpStatus status;

    public TokenException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public TokenException(AuthException e) {
        super(e.getMessage());
        this.status = e.getStatus();
    }

}
