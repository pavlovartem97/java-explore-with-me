package ru.practicum.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.StatsClient;
import ru.practicum.constraints.Constants;
import ru.practicum.dto.StatisticsInDto;
import ru.practicum.dto.StatisticsOutDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class StatsClientImpl extends BaseClient implements StatsClient {

    private static final String STATS_SERVER_URI = "http://stats-server:9090";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public StatsClientImpl(RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(STATS_SERVER_URI))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public void hit(StatisticsInDto statisticsInDto) {
        ResponseEntity<Object> post = post("/hit", statisticsInDto);

        if (post.getStatusCode() != HttpStatus.CREATED) {
            throw new RuntimeException("Can't create request to stats-server");
        }
    }

    public List<StatisticsOutDto> calcStats(LocalDateTime start, LocalDateTime end, Set<String> uris, Boolean unique) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start", start.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT)));
        parameters.put("end", end.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT)));
        parameters.put("uris", uris);
        parameters.put("unique", unique);

        ResponseEntity<Object> response = get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Can't create request to stats-server");
        }

        try {
            return objectMapper.readValue(response.getBody().toString(), new TypeReference<>(){});
        } catch (JsonProcessingException ex) {
            return null;
        }
    }
}
