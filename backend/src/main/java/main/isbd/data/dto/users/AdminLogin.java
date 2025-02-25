package main.isbd.data.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Deprecated
@AllArgsConstructor
@Getter
@Setter
public class AdminLogin {
    private Integer id;
    private String password;

    public boolean isValid() {
        return  id != null
                && password != null && !password.isEmpty();
    }
}
