package main.isbd.data.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientRegResponse {
    private String name;
    private String login;
    private String phoneNumber;
    private String email;
    private JwtPairResponse jwtPairResponse;

    public ClientRegResponse(ClientRegister clientRegister, JwtPairResponse jwtPairResponse) {
        this.name = clientRegister.getName();
        this.login = clientRegister.getLogin();
        this.phoneNumber = clientRegister.getPhoneNumber();
        this.email = clientRegister.getEmail();
        this.jwtPairResponse = jwtPairResponse;
    }

}
