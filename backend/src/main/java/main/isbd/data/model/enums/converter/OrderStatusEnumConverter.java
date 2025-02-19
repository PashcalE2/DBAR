package main.isbd.data.model.enums.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import main.isbd.data.model.enums.OrderStatusEnum;
import main.isbd.exception.BaseAppRuntimeException;
import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class OrderStatusEnumConverter implements AttributeConverter<OrderStatusEnum, String> {
    @Override
    public String convertToDatabaseColumn(OrderStatusEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public OrderStatusEnum convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        try {
            return Stream.of(OrderStatusEnum.values())
                    .filter(status -> status.getValue().equals(dbData))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }
        catch (IllegalArgumentException e) {
            throw new BaseAppRuntimeException(String.format("Неправильный статус заказа: %s", dbData), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
