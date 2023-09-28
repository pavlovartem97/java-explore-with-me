package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import ru.practicum.constraints.Constants;

import java.time.LocalDateTime;

@Value
public class EventBriefOutDto {
    String annotation;

    CategoryOutDto category;

    Long confirmedRequests;

    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    LocalDateTime eventDate;

    Long id;

    UserBriefOutDto initiator;

    Boolean paid;

    String title;

    Long views;
}
