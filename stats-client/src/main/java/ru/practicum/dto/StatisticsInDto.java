package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import ru.practicum.constraints.Constraints;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
public class StatisticsInDto {

    @JsonFormat(pattern = Constraints.DATE_TIME_FORMAT)
    @NotNull(message = "timestamp is null")
    LocalDateTime timestamp;

    @NotNull(message = "ip is null")
    String ip;

    @NotNull(message = "app is null")
    String app;

    @NotNull(message = "uri is null")
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
