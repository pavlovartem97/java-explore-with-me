package ru.practicum.dto.compilation;

import lombok.Value;
import ru.practicum.dto.event.EventBriefOutDto;

import java.util.List;

@Value
public class CompilationOutDto {
    List<EventBriefOutDto> events;

    Boolean pinned;

    String title;

    Long id;
}
