package ru.practicum.dto.category;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
public class CategoryInDto {
    @NotNull
    @Size(min = 1, max = 50)
    @NotBlank
    String name;

    @JsonCreator
    public CategoryInDto(@JsonProperty("name") String name) {
        this.name = name;
    }
}
