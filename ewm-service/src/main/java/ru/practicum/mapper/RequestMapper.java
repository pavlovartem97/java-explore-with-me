package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.dto.event.EventRequestOutDto;
import ru.practicum.model.Request;

import java.util.List;

@Mapper
public abstract class RequestMapper {

    @Mapping(target = "event", source = "request.event.id")
    @Mapping(target = "requester", source = "request.user.id")
    public abstract EventRequestOutDto map(Request request);

    public abstract List<EventRequestOutDto> map(List<Request> requests);
}
