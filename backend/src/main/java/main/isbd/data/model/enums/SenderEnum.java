package main.isbd.data.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum SenderEnum {
    CLIENT("клиент"),
    ADMIN("консультант");

    @JsonValue
    private final String value;
    SenderEnum(String value) {
        this.value = value;
    }
}
