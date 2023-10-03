package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import ru.practicum.constraints.Constants;
import ru.practicum.dto.enums.RequestStatus;

import java.time.LocalDateTime;

@Value
public class EventRequestOutDto {

    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    LocalDateTime created;

    Long event;

    Long id;

    Long requester;

    RequestStatus status;

    @JsonCreator
    public EventRequestOutDto(@JsonProperty("created") LocalDateTime created,
                              @JsonProperty("event") Long event,
                              @JsonProperty("id") Long id,
                              @JsonProperty("requester") Long requester,
                              @JsonProperty("status") RequestStatus status) {
        this.created = created;
        this.event = event;
        this.id = id;
        this.requester = requester;
        this.status = status;
    }
}
