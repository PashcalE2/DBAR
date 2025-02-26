package main.isbd.data.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FactoryRegResponse {

    private String login;
    private String phoneNumber;
    private String email;

    private String name;
    private String address;

    private JwtPairResponse jwtPairResponse;

    public FactoryRegResponse(FactoryRegister factoryRegister, JwtPairResponse jwtPairResponse) {
        this.login = factoryRegister.getLogin();
        this.phoneNumber = factoryRegister.getPhoneNumber();
        this.email = factoryRegister.getEmail();
        this.name = factoryRegister.getName();
        this.address = factoryRegister.getAddress();
        this.jwtPairResponse = jwtPairResponse;
    }

}
