package ru.practicum.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.constraints.Constraints;
import ru.practicum.dto.StatisticsInDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class StatsClient extends BaseClient {

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> hit(StatisticsInDto statisticsInDto) {
        return post("/hit", statisticsInDto);
    }

    public ResponseEntity<Object> calcStats(LocalDateTime start, LocalDateTime end, Set<String> uris, Boolean unique) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start", start.format(DateTimeFormatter.ofPattern(Constraints.DATE_TIME_FORMAT)));
        parameters.put("end", end.format(DateTimeFormatter.ofPattern(Constraints.DATE_TIME_FORMAT)));
        parameters.put("uris", uris);
        parameters.put("unique", unique);

        return get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", null, parameters);
    }
}
