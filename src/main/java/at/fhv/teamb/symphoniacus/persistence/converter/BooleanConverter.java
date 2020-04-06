package at.fhv.teamb.symphoniacus.persistence.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class BooleanConverter implements AttributeConverter<Boolean, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Boolean attribute) {
        if (attribute != null) {
            if (attribute) {
                return 1;
            } else {
                return 0;
            }

        }
        return null;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer dbData) {
        if (dbData != null) {
            return dbData.equals(1);
        }
        return null;
    }
}
