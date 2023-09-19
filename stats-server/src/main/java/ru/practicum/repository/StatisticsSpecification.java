package ru.practicum.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import ru.practicum.model.Statistics;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class StatisticsSpecification implements Specification<Statistics> {
    private final StatisticsFilter filter;

    @Override
    public Predicate toPredicate(Root<Statistics> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.greaterThanOrEqualTo(root.get("time"), filter.getStart()));
        predicates.add(cb.lessThanOrEqualTo(root.get("time"), filter.getEnd()));

        if (!CollectionUtils.isEmpty(filter.getUris())) {
            predicates.add(root.get("uri").in(filter.getUris()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
