package main.isbd.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseAppRuntimeException extends RuntimeException {
    private final HttpStatus status;

    public BaseAppRuntimeException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
