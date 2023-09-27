package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.StatisticsInDto;
import ru.practicum.dto.StatisticsOutDto;
import ru.practicum.mapper.StatisticsMapper;
import ru.practicum.model.Statistics;
import ru.practicum.repository.StatisticsFilter;
import ru.practicum.repository.StatisticsRepository;
import ru.practicum.repository.StatisticsSpecification;
import ru.practicum.repository.view.StatisticsView;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsMapper mapper;
    private final StatisticsRepository statisticsRepository;

    @Transactional
    public void hit(StatisticsInDto dto) {
        Statistics statistics = mapper.map(dto);
        statisticsRepository.save(statistics);
    }

    @Transactional(readOnly = true)
    public Collection<StatisticsOutDto> calcStats(LocalDateTime start, LocalDateTime end, Set<String> uris, Boolean unique) {
        if (start.isAfter(end)) {
            return List.of();
        }

        StatisticsFilter statisticsFilter = StatisticsFilter.builder()
                .start(start)
                .end(end)
                .uris(uris)
                .unique(unique)
                .build();
        List<StatisticsView> statistics = statisticsRepository.getStatistic(new StatisticsSpecification(statisticsFilter));
        return statistics.stream()
                .map(mapper::map)
                .collect(Collectors.toUnmodifiableList());
    }
}
