package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.StatisticsInDto;
import ru.practicum.dto.StatisticsOutDto;
import ru.practicum.service.StatisticsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void hit(@RequestBody @Valid StatisticsInDto dto) {
        statisticsService.hit(dto);
    }

    @GetMapping("/stats")
    public Collection<StatisticsOutDto> calcStats(@RequestParam LocalDateTime start,
                                                  @RequestParam LocalDateTime end,
                                                  @RequestParam(required = false) Set<String> uris,
                                                  @RequestParam(defaultValue = "false") Boolean unique) {
        return statisticsService.calcStats(start, end, uris, unique);
    }
}
