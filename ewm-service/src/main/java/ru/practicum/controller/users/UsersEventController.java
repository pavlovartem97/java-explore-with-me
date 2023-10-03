package ru.practicum.controller.users;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.event.EventBriefOutDto;
import ru.practicum.dto.event.EventInDto;
import ru.practicum.dto.event.EventOutDto;
import ru.practicum.dto.event.EventRequestOutDto;
import ru.practicum.dto.event.EventRequestUpdateStatusInDto;
import ru.practicum.dto.event.EventRequestUpdateStatusOutDto;
import ru.practicum.dto.event.UserEventUpdateInDto;
import ru.practicum.service.closed.UsersEventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
public class UsersEventController {
    private final UsersEventService usersEventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventOutDto createEvent(@PathVariable @Positive long userId,
                                   @RequestBody @Valid EventInDto dto) {
        return usersEventService.createEvent(userId, dto);
    }

    @GetMapping
    public List<EventBriefOutDto> getUserEvents(@PathVariable @Positive long userId,
                                                @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                @RequestParam(defaultValue = "10") @Positive int size) {
        return usersEventService.getUserEvents(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventOutDto getUserEvent(@PathVariable @Positive long userId,
                                    @PathVariable @Positive long eventId) {
        return usersEventService.getUserEvent(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventOutDto updateUserEvent(@RequestBody @Valid UserEventUpdateInDto dto,
                                       @PathVariable @Positive long userId,
                                       @PathVariable @Positive long eventId) {
        return usersEventService.updateUserEvent(dto, userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<EventRequestOutDto> getRequests(@PathVariable @Positive long userId,
                                                @PathVariable @Positive long eventId) {
        return usersEventService.getRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestUpdateStatusOutDto updateRequestStatus(@PathVariable @Positive long userId,
                                                              @PathVariable @Positive long eventId,
                                                              @RequestBody @Valid EventRequestUpdateStatusInDto dto) {
        return usersEventService.updateRequestStatus(userId, eventId, dto);
    }
}
