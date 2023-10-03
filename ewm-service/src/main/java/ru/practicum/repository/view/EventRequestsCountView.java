package ru.practicum.repository.view;

import lombok.Value;

@Value
public class EventRequestsCountView {
    Long count;
    Long eventId;
}
