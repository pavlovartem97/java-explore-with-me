package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.event.AdminEventUpdateInDto;
import ru.practicum.dto.event.EventOutDto;
import ru.practicum.service.admin.AdminEventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

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
}
