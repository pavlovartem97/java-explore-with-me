package ru.practicum.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.constraints.Constants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(@SuppressWarnings("NullableProblems") String value) {
        try {
            return LocalDate.parse(value, DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)).atStartOfDay();
        } catch (DateTimeParseException e1) {
            try {
                return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT));
            } catch (DateTimeParseException ignored) {
            }
        }
        return null;
    }
}
