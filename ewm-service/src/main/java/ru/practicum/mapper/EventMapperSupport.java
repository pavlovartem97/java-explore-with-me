package ru.practicum.mapper;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import ru.practicum.dto.compilation.CompilationOutDto;
import ru.practicum.dto.enums.RequestStatus;
import ru.practicum.dto.event.EventBriefOutDto;
import ru.practicum.dto.event.EventOutDto;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.RequestRepository;
import ru.practicum.repository.view.EventRequestsCountView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventMapperSupport {
    private final RequestRepository requestRepository;
    private final EventMapper eventMapper;
    private final CompilationMapper compilationMapper;
    private final EventRepository eventRepository;

    public EventOutDto mapEventToDto(Event event) {
        Long eventConfirmedRequestsCount = requestRepository.countByEventAndStatus(event, RequestStatus.CONFIRMED);
        return eventMapper.map(event, eventConfirmedRequestsCount);
    }

    public EventBriefOutDto mapEventToBriefDto(Event event) {
        Long eventConfirmedRequestsCount = requestRepository.countByEventAndStatus(event, RequestStatus.CONFIRMED);
        return eventMapper.mapBriefDto(event, eventConfirmedRequestsCount);
    }

    public List<EventBriefOutDto> mapEventsToBriefDto(List<Event> events) {
        List<EventRequestsCountView> eventRequestsCountViews = requestRepository.getConfirmedRequestCount(events);
        Map<Long, Long> eventConfirmedRequestsCountMap = eventRequestsCountViews.stream()
                .collect(Collectors.toMap(EventRequestsCountView::getEventId, EventRequestsCountView::getCount));
        return events.stream()
                .map(event -> {
                    Long eventConfirmedRequestsCount = eventConfirmedRequestsCountMap.getOrDefault(event.getId(), 0L);
                    return eventMapper.mapBriefDto(event, eventConfirmedRequestsCount);
                })
                .collect(Collectors.toUnmodifiableList());
    }

    public List<EventOutDto> mapEventsToDto(List<Event> events) {
        List<EventRequestsCountView> eventRequestsCountViews = requestRepository.getConfirmedRequestCount(events);
        Map<Long, Long> eventConfirmedRequestsCountMap = eventRequestsCountViews.stream()
                .collect(Collectors.toMap(EventRequestsCountView::getEventId, EventRequestsCountView::getCount));
        return events.stream()
                .map(event -> {
                    Long eventConfirmedRequestsCount = eventConfirmedRequestsCountMap.getOrDefault(event.getId(), 0L);
                    return eventMapper.map(event, eventConfirmedRequestsCount);
                })
                .collect(Collectors.toUnmodifiableList());
    }

    public CompilationOutDto mapCompilation(Compilation compilation) {
        List<Event> events = compilation.getEvents();
        List<EventBriefOutDto> eventBriefOutDtos = mapEventsToBriefDto(events);
        return compilationMapper.map(compilation, eventBriefOutDtos);
    }

    @Value
    public static class EventAdditionalInfo {
        Long confirmedRequestCount;
    }
}
