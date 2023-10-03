package ru.practicum.dto.event;

import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value
public class EventLocationDto {
    @NotNull
    @Min(-90)
    @Max(90)
    Double lat;

    @NotNull
    @Min(-180)
    @Max(180)
    Double lon;
}
