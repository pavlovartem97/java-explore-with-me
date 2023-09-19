package ru.practicum.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Statistics;
import ru.practicum.repository.view.StatisticsView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatisticsCustomRepositoryImpl implements StatisticsCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<StatisticsView> getStatistic(StatisticsSpecification specification) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StatisticsView> query = builder.createQuery(StatisticsView.class);
        Root<Statistics> root = query.from(Statistics.class);
        query.groupBy(root.get("app"), root.get("uri"));

        Expression<Long> countExpression = specification.getFilter().getUnique()
                ? builder.countDistinct(root.get("ip"))
                : root.get("ip");

        query.multiselect(root.get("app"), root.get("uri"), builder.count(countExpression));

        query.where(specification.toPredicate(root, query, builder));
        return entityManager.createQuery(query).getResultList();
    }
}
