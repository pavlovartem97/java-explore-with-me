package ru.practicum.controller.users;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.event.EventRequestOutDto;
import ru.practicum.service.closed.UsersRequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class UsersRequestController {
    private final UsersRequestService usersRequestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventRequestOutDto createRequest(@PathVariable long userId,
                                            @RequestParam long eventId) {
        return usersRequestService.createRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public EventRequestOutDto cancelRequest(@PathVariable long userId,
                                            @PathVariable long requestId) {
        return usersRequestService.cancelRequest(userId, requestId);
    }

    @GetMapping
    public List<EventRequestOutDto> getRequests(@PathVariable long userId) {
        return usersRequestService.getRequests(userId);
    }
}
