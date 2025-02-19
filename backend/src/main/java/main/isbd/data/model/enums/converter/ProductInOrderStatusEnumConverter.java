package main.isbd.data.model.enums.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import main.isbd.data.model.enums.ProductInOrderStatusEnum;
import main.isbd.exception.BaseAppRuntimeException;
import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ProductInOrderStatusEnumConverter implements AttributeConverter<ProductInOrderStatusEnum, String> {
    @Override
    public String convertToDatabaseColumn(ProductInOrderStatusEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public ProductInOrderStatusEnum convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        try {
            return Stream.of(ProductInOrderStatusEnum.values())
                    .filter(status -> status.getValue().equals(dbData))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }
        catch (IllegalArgumentException e) {
            throw new BaseAppRuntimeException(String.format("Неправильный статус продукции в заказе: %s", dbData), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
