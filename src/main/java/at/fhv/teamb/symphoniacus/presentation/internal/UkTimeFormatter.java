package at.fhv.teamb.symphoniacus.presentation.internal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.util.StringConverter;

/**
 * US - UK Time Converter.
 * @author : Danijel Antonijevic
 * @author : Nino Heinzle
 * @created : 10.05.20, So.
 **/
public class UkTimeFormatter {

    private UkTimeFormatter() {
        //Nicht zum erzeugen eines Objektes gedacht
        //nur zur Rückgabe eines Converters gedacht
    }

    /**
     * Converts Time of US to UK.
     * @return StringConverter to convert the time
     */
    public static StringConverter<LocalDate> getUkTimeConverter() {
        StringConverter<LocalDate> converter = (new StringConverter<>() {
            final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });
        return converter;
    }
}
