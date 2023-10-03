package ru.practicum.repository.specification;

import lombok.Builder;
import lombok.Value;
import ru.practicum.dto.enums.State;
import ru.practicum.model.Category;
import ru.practicum.model.User;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Value
public class SearchEventFilter {
    Set<User> users;
    Set<State> states;
    Set<Category> categories;
    LocalDateTime rangeStart;
    LocalDateTime rangeEnd;
    Boolean onlyAvailable;
    Boolean paid;
    String text;
    Boolean onlyCorrected;
}
