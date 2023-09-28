package ru.practicum.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class EventOutDto {
    String annotation;

    Long category;

    Long confirmedRequests;

    LocalDateTime createdOn;

    String description;

    LocalDateTime eventDate;

    Long id;

    UserShortOutDto initiator;

    Boolean paid;

    Integer participantLimit;

    LocalDateTime publishedOn;

    Boolean requestModeration;

    State state;

    String title;

    LocationDto location;

    Long views;
}
