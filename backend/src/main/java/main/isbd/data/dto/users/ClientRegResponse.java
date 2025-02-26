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

    private String login;
    private String phoneNumber;
    private String email;

    private JwtPairResponse jwtPairResponse;

    public ClientRegResponse(ActorRegister actorRegister, JwtPairResponse jwtPairResponse) {
        this.login = actorRegister.getLogin();
        this.phoneNumber = actorRegister.getPhoneNumber();
        this.email = actorRegister.getEmail();
        this.jwtPairResponse = jwtPairResponse;
    }

}
