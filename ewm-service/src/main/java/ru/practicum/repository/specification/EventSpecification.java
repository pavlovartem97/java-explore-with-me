package ru.practicum.repository.specification;

import lombok.Value;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.dto.enums.RequestStatus;
import ru.practicum.model.Event;
import ru.practicum.model.Request;
import ru.practicum.repository.filter.SearchEventFilter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;

@Value
public class EventSpecification implements Specification<Event> {

    private final SearchEventFilter filter;

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getUsers() != null) {
            predicates.add(root.get("user").in(filter.getUsers()));
        }
        if (filter.getStates() != null) {
            predicates.add(root.get("state").in(filter.getStates()));
        }
        if (filter.getCategories() != null) {
            predicates.add(root.get("category").in(filter.getCategories()));
        }
        if (filter.getRangeStart() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), filter.getRangeStart()));
        }
        if (filter.getRangeEnd() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), filter.getRangeEnd()));
        }
        if (filter.getText() != null) {
            predicates.add(criteriaBuilder.or(createLikePredicate(filter.getText(), "description", root, criteriaBuilder),
                    createLikePredicate(filter.getText(), "annotation", root, criteriaBuilder)));
        }
        if (filter.getOnlyAvailable() == Boolean.TRUE) {
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
        if (filter.getPaid() != null) {
            predicates.add(criteriaBuilder.equal(root.get("paid"), filter.getPaid()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate createLikePredicate(String searchText, String columnName, Root<Event> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(criteriaBuilder.lower(root.get(columnName)),
                criteriaBuilder.lower(criteriaBuilder.literal(searchText)));
    }
}
