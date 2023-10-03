package ru.practicum.dto.user;

import lombok.Value;

@Value
public class UserOutDto {
    Long id;

    String email;

    String name;
}
