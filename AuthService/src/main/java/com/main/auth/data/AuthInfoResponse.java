package com.main.auth.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.auth.exeptions.AuthException;
import com.main.auth.exeptions.TokenException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class AuthInfoResponse {

    private int code;
    private String message;

    public AuthInfoResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public AuthInfoResponse(HttpStatus status, String message) {
        this.code = status.value();
        this.message = message;
    }

    public AuthInfoResponse(AuthException e) {
        this.code = e.getStatus().value();
        this.message = e.getMessage();
    }

    public AuthInfoResponse(TokenException e) {
        this.code = e.getStatus().value();
        this.message = e.getMessage();
    }

    public String convertToJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
