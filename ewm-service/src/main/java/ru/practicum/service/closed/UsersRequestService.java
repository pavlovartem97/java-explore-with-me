package ru.practicum.service.closed;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.enums.RequestStatus;
import ru.practicum.dto.enums.State;
import ru.practicum.dto.event.EventRequestOutDto;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.model.Event;
import ru.practicum.model.Request;
import ru.practicum.model.User;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.RequestRepository;
import ru.practicum.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersRequestService {

    private final RequestMapper requestMapper;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;

    @Transactional
    public EventRequestOutDto createRequest(long userId, long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (event.getUser().equals(user)) {
            throw new ConflictException("User can't request his event");
        }
        if (event.getState() != State.PUBLISHED) {
            throw new ConflictException("User can't request non-published event");
        }

        if (event.getParticipantLimit() != 0) {
            Long participantsCount = requestRepository.countByEventAndStatus(event, RequestStatus.CONFIRMED);
            if (event.getParticipantLimit().equals(participantsCount.intValue())) {
                throw new ConflictException("Participation limit reached");
            }
        }

        RequestStatus requestStatus = !event.getRequestModeration() || event.getParticipantLimit() == 0
                ? RequestStatus.CONFIRMED
                : RequestStatus.PENDING;

        Request request = new Request();
        request.setUser(user);
        request.setEvent(event);
        request.setStatus(requestStatus);
        request = requestRepository.save(request);

        return requestMapper.map(request);
    }

    @Transactional
    public EventRequestOutDto cancelRequest(long userId, long requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!request.getUser().equals(user)) {
            throw new NotFoundException("Request not found by this user");
        }

        request.setStatus(RequestStatus.CANCELED);
        return requestMapper.map(request);
    }

    @Transactional(readOnly = true)
    public List<EventRequestOutDto> getRequests(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Request> requests = requestRepository.findByUserOrderById(user);
        return requests.stream()
                .map(requestMapper::map)
                .collect(Collectors.toUnmodifiableList());
    }
}
