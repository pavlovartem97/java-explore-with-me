package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.dto.enums.RequestStatus;
import ru.practicum.model.Event;
import ru.practicum.model.Request;
import ru.practicum.model.User;
import ru.practicum.repository.view.EventRequestsCountView;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Long countByEventAndStatus(Event event, RequestStatus status);

    List<Request> findByUserOrderById(User user);

    List<Request> findByEventOrderById(Event event);

    List<Request> findByIdIn(List<Long> requestIds);

    List<Request> findByStatusAndEventOrderById(RequestStatus requestStatus, Event event);

    @Query("SELECT COUNT(r.event.id), r.event.id " +
            "FROM Request r " +
            "WHERE r.event IN (:events) AND r.status = 'CONFIRMED' " +
            "GROUP BY r.event.id ")
    List<EventRequestsCountView> getConfirmedRequestCount(@Param("events") List<Event> events);
}
