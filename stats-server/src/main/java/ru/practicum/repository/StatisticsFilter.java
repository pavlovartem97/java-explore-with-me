package ru.practicum.repository;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value
@Builder
public class StatisticsFilter {
    LocalDateTime start;
    LocalDateTime end;
    Set<String> uris;
    Boolean unique;
}
