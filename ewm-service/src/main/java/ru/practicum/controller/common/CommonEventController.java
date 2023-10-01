package ru.practicum.controller.common;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.enums.SortType;
import ru.practicum.dto.event.EventOutDto;
import ru.practicum.service.common.CommonEventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@Validated
public class CommonEventController {
    private final CommonEventService commonEventService;

    @GetMapping("/{id}")
    public EventOutDto getEvent(@PathVariable long id) {
        return commonEventService.getEvent(id);
    }

    @GetMapping
    public List<EventOutDto> getEvents(@RequestParam(required = false) String text,
                                       @RequestParam(required = false) Set<Long> categories,
                                       @RequestParam(required = false) Boolean paid,
                                       @RequestParam(required = false) LocalDateTime rangeStart,
                                       @RequestParam(required = false) LocalDateTime rangeEnd,
                                       @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                       @RequestParam(defaultValue = "EVENT_DATE") SortType sortType,
                                       @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                       @RequestParam(defaultValue = "10") @Positive int size) {
        return commonEventService.getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sortType, from, size);
    }
}
