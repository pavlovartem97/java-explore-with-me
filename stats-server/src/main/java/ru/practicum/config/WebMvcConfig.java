package ru.practicum.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.practicum.constraints.Constants;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new LocalDateTimeConverter());
    }

    private static class LocalDateTimeConverter implements Converter<String, LocalDateTime> {
        @Override
        public LocalDateTime convert(@SuppressWarnings("NullableProblems") String value) {
            try {
                return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT));
            } catch (DateTimeParseException ex) {
                throw new ValidationException("Date in wrong format");
            }
        }
    }
}