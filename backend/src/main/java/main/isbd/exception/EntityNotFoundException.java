package main.isbd.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BaseAppException {
    public EntityNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
