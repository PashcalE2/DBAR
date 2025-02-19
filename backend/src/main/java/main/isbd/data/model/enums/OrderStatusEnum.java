package main.isbd.data.model.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    BEING_FORMED("формируется"),
    AWAITS_PAYMENT("ожидает оплаты"),
    IN_PROGRESS("выполняется"),
    DONE("выполнен"),
    CANCELED("отклонен");

    private final String value;
    OrderStatusEnum(String value) {
        this.value = value;
    }
}
