package ru.practicum.mapper;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.practicum.dto.compilation.CompilationOutDto;
import ru.practicum.dto.enums.RequestStatus;
import ru.practicum.dto.event.EventBriefOutDto;
import ru.practicum.dto.event.EventOutDto;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;
import ru.practicum.repository.RequestRepository;
import ru.practicum.repository.view.EventRequestsCountView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventMapperSupport {
    private final RequestRepository requestRepository;
    private final EventMapper eventMapper;
    private final CompilationMapper compilationMapper;

    public EventOutDto mapEventToDto(Event event) {
        return mapEventToDto(event, null, List.of());
    }

    public EventOutDto mapEventToDto(Event event, @Nullable Long views) {
        return mapEventToDto(event, views, List.of());
    }

    public EventOutDto mapEventToDto(Event event, @Nullable Long views, List<String> comments) {
        Long eventConfirmedRequestsCount = requestRepository.countByEventAndStatus(event, RequestStatus.CONFIRMED);
        return eventMapper.map(event, eventConfirmedRequestsCount, views, comments);
    }

    public List<EventBriefOutDto> mapEventsToBriefDto(List<Event> events) {
        return mapEventsToBriefDto(events, new HashMap<>());
    }

    public List<EventBriefOutDto> mapEventsToBriefDto(List<Event> events, Map<Long, Long> eventViews) {
        List<EventRequestsCountView> eventRequestsCountViews = requestRepository.getConfirmedRequestCount(events);
        Map<Long, Long> eventConfirmedRequestsCountMap = eventRequestsCountViews.stream()
                .collect(Collectors.toMap(EventRequestsCountView::getEventId, EventRequestsCountView::getCount));
        return events.stream()
                .map(event -> {
                    Long eventConfirmedRequestsCount = eventConfirmedRequestsCountMap.getOrDefault(event.getId(), 0L);
                    return eventMapper.mapBriefDto(event, eventConfirmedRequestsCount, eventViews.get(event.getId()));
                })
                .collect(Collectors.toUnmodifiableList());
    }

    public List<EventOutDto> mapEventsToDto(List<Event> events, Map<Long, Long> eventViews) {
        return mapEventsToDto(events, eventViews, new HashMap<>());
    }


    public List<EventOutDto> mapEventsToDto(List<Event> events, @Nullable Map<Long, Long> eventViews, @Nullable Map<Long, List<String>> commentsMap) {
        List<EventRequestsCountView> eventRequestsCountViews = requestRepository.getConfirmedRequestCount(events);
        Map<Long, Long> eventConfirmedRequestsCountMap = eventRequestsCountViews.stream()
                .collect(Collectors.toMap(EventRequestsCountView::getEventId, EventRequestsCountView::getCount));
        return events.stream()
                .map(event -> {
                    Long eventConfirmedRequestsCount = eventConfirmedRequestsCountMap.getOrDefault(event.getId(), 0L);
                    List<String> comments = commentsMap != null ? commentsMap.getOrDefault(event.getId(), List.of()) : null;
                    Long views = eventViews != null ? eventViews.getOrDefault(event.getId(), null) : null;
                    return eventMapper.map(event, eventConfirmedRequestsCount, views, comments);
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
