package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.dto.compilation.CompilationOutDto;
import ru.practicum.dto.event.EventBriefOutDto;
import ru.practicum.model.Compilation;

import java.util.List;

@Mapper(imports = EventMapper.class)
public abstract class CompilationMapper {

    @Mapping(source = "events", target = "events")
    public abstract CompilationOutDto map(Compilation compilation, List<EventBriefOutDto> events);
}
