package main.isbd.controllers;

import lombok.extern.slf4j.Slf4j;
import main.isbd.data.AppInfoResponse;
import main.isbd.exception.BaseAppException;
import main.isbd.exception.BaseAppRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class DefaultAdvise {
    @ExceptionHandler(BaseAppException.class)
    public ResponseEntity<AppInfoResponse> handleBaseAppException(BaseAppException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppInfoResponse(e.getMessage(), e.getStatus()), e.getStatus());
    }

    @ExceptionHandler(BaseAppRuntimeException.class)
    public ResponseEntity<AppInfoResponse> handleBaseAppRuntimeException(BaseAppRuntimeException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppInfoResponse(e.getMessage(), e.getStatus()), e.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppInfoResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppInfoResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
