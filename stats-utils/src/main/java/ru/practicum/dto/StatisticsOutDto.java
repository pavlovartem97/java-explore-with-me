package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/** Выходная ДТО */
@Value
public class StatisticsOutDto {

    String app;

    String uri;

    Long hits;

    @JsonCreator
    public StatisticsOutDto(@JsonProperty("uri") String uri,
                            @JsonProperty("app") String app,
                            @JsonProperty("hits") Long hits) {
        this.app = app;
        this.hits = hits;
        this.uri = uri;
    }
}
