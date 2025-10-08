package games.entities.persistence;

import games.entities.EsrbRating;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false) // lo aplicamos solo donde lo anotemos
public class EsrbRatingConverter implements AttributeConverter<EsrbRating, String> {

    @Override
    public String convertToDatabaseColumn(EsrbRating attribute) {
        return (attribute == null) ? null : attribute.code(); // guarda "E", "E10+", etc.
    }

    @Override
    public EsrbRating convertToEntityAttribute(String dbData) {
        return EsrbRating.fromRaw(dbData); // lee String -> enum
    }
}
