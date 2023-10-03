package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.enums.State;
import ru.practicum.dto.event.AdminEventUpdateInDto;
import ru.practicum.dto.event.EventOutDto;
import ru.practicum.service.admin.AdminEventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
@Validated
public class AdminEventController {
    private final AdminEventService adminEventService;

    @PatchMapping("/{eventId}")
    public EventOutDto updateUserEvent(@RequestBody @Valid AdminEventUpdateInDto dto,
                                       @PathVariable @Positive long eventId) {
        return adminEventService.updateEvent(eventId, dto);
    }

    @GetMapping
    public List<EventOutDto> getEvents(@RequestParam(required = false) Set<Long> users,
                                       @RequestParam(required = false) Set<State> states,
                                       @RequestParam(required = false) Set<Long> categories,
                                       @RequestParam(required = false) LocalDateTime rangeStart,
                                       @RequestParam(required = false) LocalDateTime rangeEnd,
                                       @RequestParam(defaultValue = "false") Boolean onlyCorrected,
                                       @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                       @RequestParam(defaultValue = "10") @Positive int size) {
        return adminEventService.getEvents(users, states, categories, rangeStart, rangeEnd, onlyCorrected, from, size);
    }
}
