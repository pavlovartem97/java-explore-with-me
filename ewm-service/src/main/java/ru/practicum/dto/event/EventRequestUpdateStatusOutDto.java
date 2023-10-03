package ru.practicum.dto.event;

import lombok.Value;

import java.util.List;

@Value
public class EventRequestUpdateStatusOutDto {
    List<EventRequestOutDto> confirmedRequests;
    List<EventRequestOutDto> rejectedRequests;
}
