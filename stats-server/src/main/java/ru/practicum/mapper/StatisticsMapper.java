package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.dto.StatisticsInDto;
import ru.practicum.dto.StatisticsOutDto;
import ru.practicum.model.Statistics;
import ru.practicum.repository.view.StatisticsView;

import java.time.LocalDateTime;

@Mapper(imports = {LocalDateTime.class})
public abstract class StatisticsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "time", source = "timestamp")
    public abstract Statistics map(StatisticsInDto dto);

    @Mapping(target = "hits", source = "count")
    public abstract StatisticsOutDto map(StatisticsView view);
}
