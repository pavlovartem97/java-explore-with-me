package ru.practicum.validation;

import ru.practicum.dto.StatisticsInDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class StatisticsInDtoValidator implements ConstraintValidator<StatisticsInDtoValid, StatisticsInDto> {

    @Override
    public void initialize(StatisticsInDtoValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(StatisticsInDto dto, ConstraintValidatorContext constraintValidatorContext) {
        LocalDateTime start = dto.getTimestamp();
        String app = dto.getApp();
        String uri = dto.getUri();
        String ip = dto.getIp();

        if (start == null || app == null || uri == null || ip == null) {
            return false;
        }
        return app.length() <= 255 && uri.length() <= 255 && ip.length() <= 255;
    }

}
