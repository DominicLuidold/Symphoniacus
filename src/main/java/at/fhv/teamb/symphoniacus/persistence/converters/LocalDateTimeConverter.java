package at.fhv.teamb.symphoniacus.persistence.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;
import java.sql.Timestamp;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {


    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {
        if (attribute != null) {
            return Timestamp.valueOf(attribute);
        }
        return null;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp dbData) {
        if (dbData != null) {
            return dbData.toLocalDateTime();
        }
        return null;
    }
}
