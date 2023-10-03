package ru.practicum.dto.compilation;

import lombok.Value;

import javax.validation.constraints.Size;
import java.util.List;

@Value
public class CompilationUpdateInDto {
    List<Long> events;

    Boolean pinned;

    // todo NotBlunk
    @Size(min = 1, max = 50)
    String title;
}
