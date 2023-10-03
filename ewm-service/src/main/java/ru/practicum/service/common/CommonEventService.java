package ru.practicum.service.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.StatsClient;
import ru.practicum.dto.StatisticsInDto;
import ru.practicum.dto.StatisticsOutDto;
import ru.practicum.dto.enums.SortType;
import ru.practicum.dto.enums.State;
import ru.practicum.dto.event.EventOutDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.EventMapperSupport;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.filter.SearchEventFilter;
import ru.practicum.repository.specification.EventSpecification;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonEventService {
    private final EventRepository eventRepository;
    private final EventMapperSupport eventMapperSupport;
    private final CategoryRepository categoryRepository;
    private final StatsClient statsClient;

    private static final String EWM_SERVICE_NAME = "ewm-main-service";

    @Transactional(readOnly = true)
    public EventOutDto getEvent(long eventId, HttpServletRequest request) {
        hitStatisticsClient(request);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));
        if (event.getState() != State.PUBLISHED) {
            throw new NotFoundException("Event was not published");
        }
        Map<Long, Long> views = getViews(List.of(event));
        return eventMapperSupport.mapEventToDto(event, views.get(eventId));
    }

    @Transactional(readOnly = true)
    public List<EventOutDto> getEvents(String text,
                                       Set<Long> categoryId,
                                       Boolean paid,
                                       LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd,
                                       Boolean onlyAvailable,
                                       SortType sortType,
                                       @PositiveOrZero int from,
                                       @Positive int size,
                                       HttpServletRequest request) {
        hitStatisticsClient(request);
        if (rangeStart != null && rangeEnd != null && rangeEnd.isBefore(rangeStart)) {
            throw new ValidationException("rangeEnd can't be before rangeStart");
        }
        Set<Category> categories = categoryRepository.findByIdIn(categoryId);
        SearchEventFilter filter = SearchEventFilter.builder()
                .rangeStart(rangeStart != null ? rangeStart : LocalDateTime.now())
                .rangeEnd(rangeEnd)
                .states(Set.of(State.PUBLISHED))
                .categories(categories)
                .paid(paid)
                .text(text)
                .onlyAvailable(onlyAvailable)
                .build();
        EventSpecification eventSpecification = new EventSpecification(filter);

        List<Event> events = List.of();
        Map<Long, Long> views = new HashMap<>();
        switch (sortType) {
            case EVENT_DATE: {
                PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "eventDate"));
                events = eventRepository.findAll(eventSpecification, pageRequest).getContent();
                break;
            }
            case VIEWS: {
                events = eventRepository.findAll(eventSpecification);
                Map<Long, Long> eventViews = getViews(events);
                events = events.stream()
                        .sorted(Comparator.comparingLong(event -> -eventViews.getOrDefault(event.getId(), 0L)))
                        .skip(from)
                        .limit(size)
                        .collect(Collectors.toUnmodifiableList());
                views = eventViews;
                break;
            }
        }
        return eventMapperSupport.mapEventsToDto(events, views);
    }

    private void hitStatisticsClient(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
        StatisticsInDto dto = new StatisticsInDto(LocalDateTime.now(), ip, EWM_SERVICE_NAME, uri);
        statsClient.hit(dto);
    }

    private Map<Long, Long> getViews(List<Event> events) {
        String getEventUriPrefix = "/events/";
        Set<String> eventUris = events.stream()
                .map(event -> getEventUriPrefix + event.getId())
                .collect(Collectors.toUnmodifiableSet());
        LocalDateTime earliestEventDate = events.stream()
                .map(Event::getEventDate)
                .min(LocalDateTime::compareTo)
                .get().minusYears(1);
        List<StatisticsOutDto> statisticsOutDtos = statsClient.calcStats(earliestEventDate, LocalDateTime.now().plusYears(1), eventUris, Boolean.TRUE);
        return statisticsOutDtos.stream()
                .collect(Collectors.toMap(statistic -> Long.parseLong(statistic.getUri().substring(getEventUriPrefix.length())), StatisticsOutDto::getHits));
    }
}
