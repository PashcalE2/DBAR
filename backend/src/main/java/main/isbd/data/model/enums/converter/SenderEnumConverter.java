package main.isbd.data.model.enums.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import main.isbd.data.model.enums.ProductInOrderStatusEnum;
import main.isbd.data.model.enums.SenderEnum;
import main.isbd.exception.BaseAppRuntimeException;
import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class SenderEnumConverter implements AttributeConverter<SenderEnum, String> {
    @Override
    public String convertToDatabaseColumn(SenderEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public SenderEnum convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        try {
            return Stream.of(SenderEnum.values())
                    .filter(status -> status.getValue().equals(dbData))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }
        catch (IllegalArgumentException e) {
            throw new BaseAppRuntimeException(String.format("Неправильный статус отправителя: %s", dbData), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
