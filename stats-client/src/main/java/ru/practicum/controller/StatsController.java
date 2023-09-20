package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.StatisticsInDto;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@Validated
public class StatsController {
    private final StatsClient userClient;

    @PostMapping("/hit")
    public ResponseEntity<Object> hit(@RequestBody @Valid StatisticsInDto dto) {
        return userClient.hit(dto);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> calcStats(@RequestParam LocalDateTime start,
                                            @RequestParam LocalDateTime end,
                                            @RequestParam(required = false) Set<String> uris,
                                            @RequestParam(defaultValue = "false") Boolean unique) {
        if (start.isAfter(end)) {
            throw new ValidationException("Start is after end");
        }
        return userClient.calcStats(start, end, uris, unique);
    }
}
