package main.isbd.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(
            RestTemplateBuilder restTemplateBuilder,
            RestTemplateExceptionHandler restTemplateExceptionHandler) {
        return restTemplateBuilder.errorHandler(restTemplateExceptionHandler).build();
    }

}
