package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import ru.practicum.constraints.Constants;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Optional;

@Value
public class EventInDto {
    @NotNull
    @Size(max = 2000, min = 20)
    String annotation;

    @NotNull
    Long category;

    @NotNull
    @Size(max = 7000, min = 20)
    String description;

    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    @NotNull
    @Future
    LocalDateTime eventDate;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    @Size(min = 3, max = 120)
    @NotNull
    String title;

    @NotNull
    EventLocationDto location;

    @JsonCreator
    public EventInDto(@JsonProperty("annotation") String annotation,
                      @JsonProperty("category") Long category,
                      @JsonProperty("description") String description,
                      @JsonProperty("eventDate") LocalDateTime eventDate,
                      @JsonProperty("paid") Boolean paid,
                      @JsonProperty("participantLimit") Integer participantLimit,
                      @JsonProperty("requestModeration") Boolean requestModeration,
                      @JsonProperty("title") String title,
                      @JsonProperty("location") EventLocationDto location) {
        this.annotation = annotation;
        this.category = category;
        this.description = description;
        this.eventDate = eventDate;
        this.paid = Optional.ofNullable(paid).orElse(Boolean.FALSE);
        this.participantLimit = Optional.ofNullable(participantLimit).orElse(0);
        this.requestModeration = Optional.ofNullable(requestModeration).orElse(Boolean.TRUE);
        this.title = title;
        this.location = location;
    }
}
