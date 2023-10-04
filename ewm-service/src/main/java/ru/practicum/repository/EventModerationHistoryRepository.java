package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Event;
import ru.practicum.model.EventModerationHistory;

import java.util.List;

public interface EventModerationHistoryRepository extends JpaRepository<EventModerationHistory, Long> {
    List<EventModerationHistory> findByEventInAndCommentIsNotNullOrderByIdDesc(List<Event> events);

    List<EventModerationHistory> findByEvent(Event event);
}
