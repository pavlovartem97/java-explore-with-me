package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByIdInOrderById(Collection<Long> ids);

    Set<User> findByIdIn(Collection<Long> ids);
}
