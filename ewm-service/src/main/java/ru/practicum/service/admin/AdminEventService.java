package ru.practicum.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.enums.State;
import ru.practicum.dto.event.AdminEventUpdateInDto;
import ru.practicum.dto.event.EventOutDto;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.EventMapperSupport;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.User;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.repository.filter.SearchEventFilter;
import ru.practicum.repository.specification.EventSpecification;

import javax.validation.ValidationException;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminEventService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final EventMapperSupport eventMapperSupport;
    private final UserRepository userRepository;

    @Transactional
    public EventOutDto updateEvent(long eventId, AdminEventUpdateInDto dto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));

        if (event.getState() != State.PENDING) {
            throw new ConflictException("Published events can't be updated");
        }

        if (dto.getTitle() != null) {
            event.setTitle(dto.getTitle());
        }
        if (dto.getPaid() != null) {
            event.setPaid(dto.getPaid());
        }
        if (dto.getRequestModeration() != null) {
            event.setRequestModeration(event.getRequestModeration());
        }
        if (dto.getEventDate() != null) {
            if (dto.getEventDate().minusHours(1).isBefore(LocalDateTime.now())) {
                throw new ConflictException("Event can't be edited 1 hours before eventDate");
            }
            event.setEventDate(event.getEventDate());
        }
        if (dto.getCategory() != null) {
            Category category = categoryRepository.findById(dto.getCategory())
                    .orElseThrow(() -> new NotFoundException("Category not found"));
            event.setCategory(category);
        }
        if (dto.getAnnotation() != null) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (dto.getDescription() != null) {
            event.setDescription(dto.getDescription());
        }
        if (dto.getLocation() != null) {
            event.setLat(dto.getLocation().getLat());
            event.setLon(dto.getLocation().getLon());
        }
        if (dto.getParticipantLimit() != null) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getStateAction() != null) {
            switch (dto.getStateAction()) {
                case PUBLISH_EVENT:
                    event.setState(State.PUBLISHED);
                    event.setPublishedOn(LocalDateTime.now());
                    break;
                case REJECT_EVENT:
                    event.setState(State.CANCELED);
                    break;
            }
        }
        return eventMapperSupport.mapEventToDto(event);
    }

    @Transactional(readOnly = true)
    public List<EventOutDto> getEvents(Set<Long> userIds,
                                       Set<State> states,
                                       Set<Long> categoryIds,
                                       LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd,
                                       @PositiveOrZero int from,
                                       @Positive int size) {
        if (rangeStart != null && rangeEnd != null && rangeEnd.isBefore(rangeStart)) {
            throw new ValidationException("rangeEnd can't be before rangeStart");
        }
        Set<User> users = Optional.ofNullable(userIds).map(userRepository::findByIdIn).orElse(null);
        Set<Category> categories = Optional.ofNullable(categoryIds).map(categoryRepository::findByIdIn).orElse(null);
        SearchEventFilter filter = SearchEventFilter.builder()
                .categories(categories)
                .users(users)
                .states(states)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .build();
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by("id"));
        Page<Event> events = eventRepository.findAll(new EventSpecification(filter), pageRequest);
        return eventMapperSupport.mapEventsToDto(events.getContent());
    }
}
