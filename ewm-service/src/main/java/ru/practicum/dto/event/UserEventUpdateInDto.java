package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import ru.practicum.constraints.Constants;
import ru.practicum.dto.enums.UserStateAction;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Value
public class UserEventUpdateInDto {
    @Size(max = 2000, min = 20)
    String annotation;

    Long category;

    @Size(max = 7000, min = 20)
    String description;

    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    @Future
    LocalDateTime eventDate;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    UserStateAction stateAction;

    @Size(min = 3, max = 120)
    String title;

    EventLocationDto location;

    @JsonCreator
    public UserEventUpdateInDto(@JsonProperty("annotation") String annotation,
                                @JsonProperty("category") Long category,
                                @JsonProperty("description") String description,
                                @JsonProperty("eventDate") LocalDateTime eventDate,
                                @JsonProperty("paid") Boolean paid,
                                @JsonProperty("participantLimit") Integer participantLimit,
                                @JsonProperty("requestModeration") Boolean requestModeration,
                                @JsonProperty("title") String title,
                                @JsonProperty("stateAction") UserStateAction stateAction,
                                @JsonProperty("location") EventLocationDto location) {
        this.annotation = annotation;
        this.category = category;
        this.description = description;
        this.eventDate = eventDate;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.requestModeration = requestModeration;
        this.title = title;
        this.stateAction = stateAction;
        this.location = location;
    }
}
