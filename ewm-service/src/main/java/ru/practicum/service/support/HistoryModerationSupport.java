package ru.practicum.service.support;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.model.Event;
import ru.practicum.model.EventModerationHistory;
import ru.practicum.repository.EventModerationHistoryRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryModerationSupport {

    private final EventModerationHistoryRepository eventModerationHistoryRepository;

    public List<String> getModerationComments(Event event) {
        List<EventModerationHistory> moderationHistories = event.getModerationHistories();
        List<String> comments = moderationHistories.stream()
                .map(EventModerationHistory::getComment)
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableList());
        return comments;
    }

    public Map<Long, List<String>> getModerationCommentsMap(List<Event> events) {
        List<EventModerationHistory> histories = eventModerationHistoryRepository.findByEventInAndCommentIsNotNullOrderByIdDesc(events);
        Map<Long, List<String>> commentMap = histories.stream()
                .collect(Collectors.groupingBy(EventModerationHistory::getId, Collectors.mapping(EventModerationHistory::getComment,
                        Collectors.toList())));
        return commentMap;
    }
}
