package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.dto.enums.RequestStatus;
import ru.practicum.model.Event;
import ru.practicum.model.Request;
import ru.practicum.model.User;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Long countByEventAndStatus(Event event, RequestStatus status);

    List<Request> findByUserOrderById(User user);

    List<Request> findByEventOrderById(Event event);

    List<Request> findByIdIn(List<Long> requestIds);

    List<Request> findByStatusAndEventOrderById(RequestStatus requestStatus, Event event);
}
