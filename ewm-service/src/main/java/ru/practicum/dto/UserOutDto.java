package ru.practicum.dto;

import lombok.Value;

@Value
public class UserOutDto {
    Long id;

    String email;

    String name;
}
