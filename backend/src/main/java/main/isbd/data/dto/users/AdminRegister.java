package main.isbd.data.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminRegister {

    private String phoneNumber;
    private String email;
    private String login;
    private String password;

    private String fullName;

    private String supportName;
    private String supportPhoneNumber;
    private String supportEmail;
    private String supportAddress;

    private Integer scheduleHours;
    private String scheduleDescription;

}
