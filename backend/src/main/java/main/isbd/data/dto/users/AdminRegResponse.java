package main.isbd.data.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.isbd.data.model.Admin;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminRegResponse {

    private String login;
    private String phoneNumber;
    private String email;

    private String fullName;

    private Integer clientServiceId;
    private Integer scheduleId;

    private JwtPairResponse jwtPairResponse;

    public AdminRegResponse(AdminRegister adminRegister, Admin admin, JwtPairResponse jwtPairResponse) {
        this.login = adminRegister.getLogin();
        this.phoneNumber = adminRegister.getPhoneNumber();
        this.email = adminRegister.getEmail();
        this.fullName = admin.getFullName();
        this.clientServiceId = admin.getClientServiceId();
        this.scheduleId = admin.getScheduleId();
        this.jwtPairResponse = jwtPairResponse;
    }

}
