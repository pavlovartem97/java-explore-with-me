package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.dto.event.EventBriefOutDto;
import ru.practicum.dto.event.EventInDto;
import ru.practicum.dto.event.EventLocationDto;
import ru.practicum.dto.event.EventOutDto;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.User;

@Mapper(uses = {
        CategoryMapper.class,
        UserMapper.class
})
public abstract class EventMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "category")
    @Mapping(target = "lat", source = "dto.location.lat")
    @Mapping(target = "lon", source = "dto.location.lon")
    public abstract Event map(EventInDto dto, Category category, User user);

    public abstract EventLocationDto mapLocation(Event event);

    @Mapping(target = "confirmedRequests", source = "confirmedRequestCount")
    @Mapping(target = "initiator", source = "event.user")
    public abstract EventBriefOutDto mapBriefDto(Event event, Long confirmedRequestCount);

    @Mapping(target = "confirmedRequests", source = "confirmedRequestCount")
    @Mapping(target = "location", expression = "java(mapLocation(event))")
    @Mapping(target = "initiator", source = "event.user")
    public abstract EventOutDto map(Event event, Long confirmedRequestCount);
}
