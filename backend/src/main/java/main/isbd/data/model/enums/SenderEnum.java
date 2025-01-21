package main.isbd.data.model.enums;

import lombok.Getter;

@Getter
public enum SenderEnum {
    CLIENT("клиент"),
    ADMIN("консультант");

    private final String value;
    SenderEnum(String value) {
        this.value = value;
    }
}
