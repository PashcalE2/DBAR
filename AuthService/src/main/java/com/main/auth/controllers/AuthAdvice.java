package com.main.auth.controllers;

import com.main.auth.data.AuthErrorResponse;
import com.main.auth.exeptions.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AuthAdvice {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<AuthErrorResponse> handleAuthenticationException(UsernameNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new AuthErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid jwt"));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<AuthErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new AuthErrorResponse(HttpStatus.UNAUTHORIZED, "Wrong username or password"));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<AuthErrorResponse> handleBaseAppException(AuthException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AuthErrorResponse(e), e.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthErrorResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AuthErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
