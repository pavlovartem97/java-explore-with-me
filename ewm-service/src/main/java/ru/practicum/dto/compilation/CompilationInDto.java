package ru.practicum.dto.compilation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@Value
public class CompilationInDto {
    List<Long> events;

    Boolean pinned;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    String title;

    @JsonCreator
    public CompilationInDto(@JsonProperty("events") List<Long> events,
                            @JsonProperty("pinned") Boolean pinned,
                            @JsonProperty("title") String title) {
        this.events = events;
        this.pinned = Optional.ofNullable(pinned).orElse(Boolean.FALSE);
        this.title = title;
    }
}
