package main.isbd.data.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo {

    private Integer id;
    private Integer clientId;
    private Integer adminId;
    private String status;
    private Timestamp formedAt;
    private Timestamp doneAt;

}
