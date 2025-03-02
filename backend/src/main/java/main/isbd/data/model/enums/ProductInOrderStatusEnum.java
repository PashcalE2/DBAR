package main.isbd.data.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ProductInOrderStatusEnum {
    AWAITS_PRODUCTION("ожидает производства"),
    AWAITS_ASSEMBLING("ожидает сборки"),
    AWAITS_RETURNING("ожидает возвращения"),
    RETURNED("возвращена"),
    ASSEMBLED("собрана под заказ");

    @JsonValue
    private final String value;
    ProductInOrderStatusEnum(String value) {
        this.value = value;
    }
}
