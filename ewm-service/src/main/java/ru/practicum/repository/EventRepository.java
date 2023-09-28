package ru.practicum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Event;
import ru.practicum.model.User;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByUser(User user, PageRequest pageRequest);
}
