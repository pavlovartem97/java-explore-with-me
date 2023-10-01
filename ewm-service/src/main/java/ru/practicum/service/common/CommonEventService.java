package ru.practicum.service.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.enums.SortType;
import ru.practicum.dto.enums.State;
import ru.practicum.dto.event.EventOutDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.EventMapperSupport;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.specification.EventSpecification;

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

    @Transactional(readOnly = true)
    public EventOutDto getEvent(long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));
        if (event.getState() != State.PUBLISHED) {
            throw new NotFoundException("Event was not published");
        }
        return eventMapperSupport.mapEventToDto(event);
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
                                       @Positive int size) {
        if (rangeStart != null && rangeEnd != null && rangeEnd.isBefore(rangeStart)) {
            throw new ValidationException("rangeEnd can't be before rangeStart");
        }
        Set<Category> categories = categoryRepository.findByIdIn(categoryId);
        EventSpecification eventSpecification = EventSpecification.builder()
                .rangeStart(rangeStart != null ? rangeStart : LocalDateTime.now())
                .rangeEnd(rangeEnd)
                .states(Set.of(State.PUBLISHED))
                .categories(categories)
                .paid(paid)
                .text(text)
                .onlyAvailable(onlyAvailable)
                .build();
        List<Event> events = List.of();
        switch (sortType) {
            case EVENT_DATE: {
                PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "eventDate"));
                events = eventRepository.findAll(eventSpecification, pageRequest).getContent();
                break;
            }
            case VIEWS: {
                events = eventRepository.findAll(eventSpecification);
                Map<Long, Integer> eventViews = getViews(events);
                events = events.stream()
                        .sorted(Comparator.comparingInt(event -> eventViews.getOrDefault(event.getId(), 0)))
                        .skip(from)
                        .limit(size)
                        .collect(Collectors.toUnmodifiableList());
                break;
            }
        }
        return eventMapperSupport.mapEventsToDto(events);
    }

    Map<Long, Integer> getViews(List<Event> events) {
        return new HashMap<>();
    }
}
