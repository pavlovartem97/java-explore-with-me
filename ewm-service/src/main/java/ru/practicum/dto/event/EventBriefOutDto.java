package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import ru.practicum.constraints.Constants;
import ru.practicum.dto.category.CategoryOutDto;
import ru.practicum.dto.user.UserBriefOutDto;

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
