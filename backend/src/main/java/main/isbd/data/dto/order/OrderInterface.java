package main.isbd.data.dto.order;

import java.sql.Timestamp;

@Deprecated
public interface OrderInterface {
    Integer getId();
    Integer getClientId();
    Integer getAdminId();
    String getStatus();
    Timestamp getFormedAt();
    Timestamp getDoneAt();
    Float getSum();
}
