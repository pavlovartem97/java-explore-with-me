package ru.practicum.service.support;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.enums.State;
import ru.practicum.model.Event;
import ru.practicum.model.EventModerationHistory;
import ru.practicum.repository.EventModerationHistoryRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryModerationSupport {

    private final EventModerationHistoryRepository eventModerationHistoryRepository;

    public List<String> getModerationComments(Event event) {
        List<String> comments = List.of();
        if (event.getState() == State.CANCELED) {
            List<EventModerationHistory> moderationHistories = event.getModerationHistories();
            moderationHistories.forEach(moderationHistory -> {
                if (moderationHistory.getComment() != null) {
                    comments.add(moderationHistory.getComment());
                }
            });
        }
        return comments;
    }

    public Map<Long, List<String>> getModerationCommentsMap(List<Event> events) {
        Map<Long, List<String>> comments = new HashMap<>();
        List<EventModerationHistory> histories = eventModerationHistoryRepository.findByEventInAndCommentIsNotNull(events);
        histories.stream()
                .collect(Collectors.groupingBy(EventModerationHistory::getId, Collectors.mapping(EventModerationHistory::getComment,
                        Collectors.toList())));
        return comments;
    }
}
