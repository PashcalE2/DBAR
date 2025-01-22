package main.isbd.controllers;

import main.isbd.exception.BaseAppException;
import main.isbd.exception.BaseAppRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultAdvise {
    @ExceptionHandler(BaseAppException.class)
    public ResponseEntity<?> handleBaseAppException(BaseAppException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(BaseAppRuntimeException.class)
    public ResponseEntity<?> handleBaseAppRuntimeException(BaseAppRuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
