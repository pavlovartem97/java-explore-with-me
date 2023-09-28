package ru.practicum.service.closed;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EventBriefOutDto;
import ru.practicum.dto.EventInDto;
import ru.practicum.dto.EventOutDto;
import ru.practicum.dto.EventUpdateInDto;
import ru.practicum.dto.enums.State;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.User;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersEventService {

    private final EventMapper eventMapper;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Transactional
    public EventOutDto createEvent(long userId, EventInDto eventInDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Category category = categoryRepository.findById(eventInDto.getCategory())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        Event event = eventMapper.map(eventInDto, category, user);
        event = eventRepository.save(event);
        return eventMapper.map(event);
    }

    @Transactional(readOnly = true)
    public List<EventBriefOutDto> getUserEvents(long userId, int from, int size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Page<Event> events = eventRepository.findByUser(user, PageRequest.of(from / size, size));
        return events.stream()
                .map(eventMapper::mapBriefDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public EventOutDto getUserEvent(long userId, long eventId) {
        Event event = getUserEventByIdAndUserId(userId, eventId);
        return eventMapper.map(event);
    }

    @Transactional
    public EventOutDto updateUserEvent(EventUpdateInDto dto, long userId, long eventId) {
        Event event = getUserEventByIdAndUserId(userId, eventId);

        if (event.getState() == State.PUBLISHED) {
            throw new ConflictException("Published events can't be updated");
        }
        if (event.getEventDate().minusHours(2).isBefore(LocalDateTime.now())) {
            throw new ConflictException("Event can't be edited 2 hours before eventDate");
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
                case SEND_TO_REVIEW:
                    event.setState(State.PENDING);
                    break;
                case CANCEL_REVIEW:
                    event.setState(State.CANCELED);
                    break;
            }
        }
        return eventMapper.map(event);
    }

    private Event getUserEventByIdAndUserId(long userId, long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));
        if (!event.getUser().getId().equals(userId)) {
            throw new NotFoundException("Event not found for current user");
        }
        return event;
    }
}
