package ru.practicum.dto.event;

import lombok.Value;
import ru.practicum.dto.enums.RequestStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Value
public class EventRequestUpdateStatusInDto {
    @NotNull
    @NotEmpty
    List<Long> requestIds;

    @NotNull
    RequestStatus status;
}
