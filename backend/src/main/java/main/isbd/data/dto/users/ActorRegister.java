package main.isbd.data.dto.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.isbd.exception.BaseAppException;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActorRegister {

    private String phoneNumber;
    private String email;
    private String login;
    private String password;

    public String convertToJson() throws BaseAppException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new BaseAppException("Json error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
