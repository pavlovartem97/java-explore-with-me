package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import ru.practicum.constraints.Constants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Value
public class EventInDto {
    @NotNull
    @Size(max = 2000, min = 20)
    String annotation;

    @NotNull
    Long category;

    @NotNull
    @Size(max = 7000, min = 20)
    String description;

    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    @NotNull
    LocalDateTime eventDate;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    @Size(min = 3, max = 120)
    @NotNull
    String title;

    @NotNull
    LocationDto location;
}
