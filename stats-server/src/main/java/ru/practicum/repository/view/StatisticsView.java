package ru.practicum.repository.view;

import lombok.Value;

@Value
public class StatisticsView {
    String app;
    String uri;
    Long count;
}
