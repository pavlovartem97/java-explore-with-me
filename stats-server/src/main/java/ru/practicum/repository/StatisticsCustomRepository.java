package ru.practicum.repository;

import ru.practicum.repository.view.StatisticsView;

import java.util.List;

public interface StatisticsCustomRepository {

    List<StatisticsView> getStatistic(StatisticsSpecification specification);

}
