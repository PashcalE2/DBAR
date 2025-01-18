package main.isbd.data.dto.order;

import java.sql.Timestamp;

public interface MessageInterface {
    Integer getId();
    String getSender();
    String getContent();
    Timestamp getSentAt();
}
