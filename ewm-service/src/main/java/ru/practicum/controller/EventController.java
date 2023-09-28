package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.service.EventService;

@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;


}
