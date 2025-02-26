package main.isbd.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import main.isbd.exception.BaseAppException;
import main.isbd.exception.TokenException;
import org.springframework.http.HttpStatus;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class AppInfoResponse {

    private Integer code;
    private String message;

    public AppInfoResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public AppInfoResponse(String message, HttpStatus status) {
        this.code = status.value();
        this.message = message;
    }

    public AppInfoResponse(BaseAppException e) {
        this.code = e.getStatus().value();
        this.message = e.getMessage();
    }

    public AppInfoResponse(TokenException e) {
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
