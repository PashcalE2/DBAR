package main.isbd.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.isbd.data.AppInfoResponse;
import main.isbd.exception.BaseAppRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateExceptionHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            AppInfoResponse appInfo = mapper.readValue(response.getBody(), AppInfoResponse.class);
            throw new BaseAppRuntimeException(appInfo.getMessage(), HttpStatus.valueOf(appInfo.getCode()));
        } catch (IllegalArgumentException e) {
            throw new BaseAppRuntimeException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
