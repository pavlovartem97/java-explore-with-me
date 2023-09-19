package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.practicum.model.Statistics;

public interface StatisticsRepository extends JpaRepository<Statistics, Long>, JpaSpecificationExecutor<Statistics>, StatisticsCustomRepository {

}
