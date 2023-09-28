package ru.practicum.dto;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
public class UserInDto {
    @NotNull
    @Email
    @Size(min = 6, max = 254)
    String email;

    @NotNull
    @Size(min = 2, max = 250)
    String name;
}