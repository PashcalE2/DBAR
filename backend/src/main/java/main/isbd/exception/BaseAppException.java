package main.isbd.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseAppException extends Exception {
    private final HttpStatus status;

    public BaseAppException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
