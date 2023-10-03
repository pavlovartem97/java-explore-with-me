package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import ru.practicum.constraints.Constants;
import ru.practicum.dto.category.CategoryOutDto;
import ru.practicum.dto.enums.State;
import ru.practicum.dto.user.UserBriefOutDto;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class EventOutDto {
    String annotation;

    CategoryOutDto category;

    Long confirmedRequests;

    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    LocalDateTime createdOn;

    String description;

    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    LocalDateTime eventDate;

    Long id;

    UserBriefOutDto initiator;

    Boolean paid;

    Integer participantLimit;

    LocalDateTime publishedOn;

    Boolean requestModeration;

    State state;

    String title;

    EventLocationDto location;

    Long views;

    List<String> comments;
}
