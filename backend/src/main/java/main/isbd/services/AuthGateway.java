package main.isbd.services;

import main.isbd.data.AppInfoResponse;
import main.isbd.data.dto.users.ActorProfile;
import main.isbd.data.dto.users.ActorRegister;
import main.isbd.data.dto.users.JwtPairResponse;
import main.isbd.exception.BaseAppException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthGateway {

    private final String regClientUrl;
    private final String regAdminUrl;
    private final String regFactoryUrl;
    private final String crudUserUrl;
    private final String crudUserAdminUrl;
    private final RestTemplate restTemplate;

    public AuthGateway(
            @Value("${api.auth.register.client}") String regClientUrl,
            @Value("${api.auth.register.admin}") String regAdminUrl,
            @Value("${api.auth.register.factory}") String regFactoryUrl,
            @Value("${api.auth.user}") String crudUserUrl,
            @Value("${api.auth.user.admin}") String crudUserAdminUrl,
            RestTemplate restTemplate) {
        this.regClientUrl = regClientUrl;
        this.regAdminUrl = regAdminUrl;
        this.regFactoryUrl = regFactoryUrl;
        this.crudUserUrl = crudUserUrl;
        this.crudUserAdminUrl = crudUserAdminUrl;
        this.restTemplate = restTemplate;
    }

    public JwtPairResponse registerActorAsClient(ActorRegister actorRegister) throws BaseAppException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return registerActor(actorRegister, headers, regClientUrl);
    }

    public JwtPairResponse registerActorAsAdmin(ActorRegister actorRegister, String accessToken) throws BaseAppException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + accessToken);
        return registerActor(actorRegister, headers, regAdminUrl);
    }

    public JwtPairResponse registerActorAsFactory(ActorRegister actorRegister, String accessToken)
            throws BaseAppException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + accessToken);
        return registerActor(actorRegister, headers, regFactoryUrl);
    }

    public void deleteActorByHisToken(String actorsToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + actorsToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<AppInfoResponse> response = restTemplate.exchange(crudUserUrl, HttpMethod.DELETE, entity, AppInfoResponse.class);
        response.getBody();
    }

    public ActorProfile getActorAdminProfile(String adminLogin, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ActorProfile> response = restTemplate.exchange(crudUserAdminUrl + adminLogin,
                HttpMethod.GET, entity, ActorProfile.class);
        return response.getBody();
    }

    private JwtPairResponse registerActor(ActorRegister actorRegister, HttpHeaders headers, String apiUrl)
            throws BaseAppException {
        HttpEntity<String> entity = new HttpEntity<>(actorRegister.convertToJson(), headers);
        ResponseEntity<JwtPairResponse> response = restTemplate.exchange(apiUrl, HttpMethod.POST,
                entity, JwtPairResponse.class);
        return response.getBody();
    }

}
