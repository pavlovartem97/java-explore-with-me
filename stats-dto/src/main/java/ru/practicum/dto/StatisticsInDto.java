package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.time.LocalDateTime;

import static ru.practicum.constraints.Constraints.DATE_TIME_FORMAT;

@Value
public class StatisticsInDto {

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    LocalDateTime timestamp;

    String ip;

    String app;

    String uri;

    @JsonCreator
    public StatisticsInDto(@JsonProperty("timestamp") LocalDateTime timestamp,
                           @JsonProperty("ip") String ip,
                           @JsonProperty("app") String app,
                           @JsonProperty("uri") String uri) {
        this.app = app;
        this.timestamp = timestamp;
        this.ip = ip;
        this.uri = uri;
    }
}
