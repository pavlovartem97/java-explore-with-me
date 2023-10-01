package ru.practicum.repository.specification;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.dto.enums.RequestStatus;
import ru.practicum.dto.enums.State;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.Request;
import ru.practicum.model.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder
@Value
public class EventSpecification implements Specification<Event> {
    Set<User> users;
    Set<State> states;
    Set<Category> categories;
    LocalDateTime rangeStart;
    LocalDateTime rangeEnd;
    Boolean onlyAvailable;
    Boolean paid;
    String text;

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (users != null) {
            predicates.add(root.get("user").in(users));
        }
        if (states != null) {
            predicates.add(root.get("state").in(states));
        }
        if (categories != null) {
            predicates.add(root.get("category").in(categories));
        }
        if (rangeStart != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), rangeStart));
        }
        if (rangeEnd != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), rangeEnd));
        }
        if (text != null) {
            predicates.add(criteriaBuilder.or(createLikePredicate(text, "description", root, criteriaBuilder),
                    createLikePredicate(text, "annotation", root, criteriaBuilder)));
        }
        if (onlyAvailable == Boolean.TRUE) {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Event> eventRoot = subquery.from(Event.class);
            Join<Event, Request> eventRequestRoot = eventRoot.join("requests", JoinType.LEFT);
            subquery.select(eventRoot.get("id"))
                    .groupBy(eventRoot.get("id"))
                    .having(criteriaBuilder.lessThanOrEqualTo(criteriaBuilder.count(eventRoot.get("id")), eventRoot.get("participantLimit")))
                    .where(criteriaBuilder.or(criteriaBuilder.equal(eventRequestRoot.get("status"), RequestStatus.CONFIRMED),
                            criteriaBuilder.isNull(eventRequestRoot.get("status"))));
            Predicate requestLimitIsZero = criteriaBuilder.equal(root.get("participantLimit"), 0);
            Predicate requestLessThanLimit = root.get("id").in(subquery);
            predicates.add(criteriaBuilder.or(requestLessThanLimit, requestLimitIsZero));
        }
        if (paid != null) {
            predicates.add(criteriaBuilder.equal(root.get("paid"), paid));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate createLikePredicate(String searchText, String columnName, Root<Event> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(criteriaBuilder.lower(root.get(columnName)),
                criteriaBuilder.lower(criteriaBuilder.literal(searchText)));
    }
}
