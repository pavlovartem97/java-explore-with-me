package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import ru.practicum.constraints.Constants;
import ru.practicum.dto.category.CategoryOutDto;
import ru.practicum.dto.enums.State;
import ru.practicum.dto.user.UserBriefOutDto;

import java.time.LocalDateTime;

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

    @JsonCreator
    public EventOutDto(@JsonProperty("annotation") String annotation,
                       @JsonProperty("category") CategoryOutDto category,
                       @JsonProperty("confirmedRequests") Long confirmedRequests,
                       @JsonProperty("createdOn") LocalDateTime createdOn,
                       @JsonProperty("description") String description,
                       @JsonProperty("eventDate") LocalDateTime eventDate,
                       @JsonProperty("id") Long id,
                       @JsonProperty("initiator") UserBriefOutDto initiator,
                       @JsonProperty("paid") Boolean paid,
                       @JsonProperty("participantLimit") Integer participantLimit,
                       @JsonProperty("publishedOn") LocalDateTime publishedOn,
                       @JsonProperty("requestModeration") Boolean requestModeration,
                       @JsonProperty("state") State state,
                       @JsonProperty("title") String title,
                       @JsonProperty("location") EventLocationDto location,
                       @JsonProperty("views") Long views) {
        this.annotation = annotation;
        this.category = category;
        this.confirmedRequests = confirmedRequests;
        this.createdOn = createdOn;
        this.description = description;
        this.eventDate = eventDate;
        this.id = id;
        this.initiator = initiator;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.publishedOn = publishedOn;
        this.requestModeration = requestModeration;
        this.state = state;
        this.title = title;
        this.location = location;
        this.views = views;
    }
}
