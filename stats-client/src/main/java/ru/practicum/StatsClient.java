package ru.practicum;

import org.springframework.lang.Nullable;
import ru.practicum.dto.StatisticsInDto;
import ru.practicum.dto.StatisticsOutDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface StatsClient {
    void hit(StatisticsInDto statisticsInDto);

    List<StatisticsOutDto> calcStats(LocalDateTime start, LocalDateTime end, @Nullable Set<String> uris, @Nullable Boolean unique);
}
