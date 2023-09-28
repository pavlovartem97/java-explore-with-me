package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.dto.UserInDto;
import ru.practicum.dto.UserOutDto;
import ru.practicum.dto.UserBriefOutDto;
import ru.practicum.model.User;

import java.util.List;

@Mapper
public abstract class UserMapper {
    @Mapping(target = "id", ignore = true)
    public abstract User map(UserInDto dto);

    public abstract UserOutDto map(User user);

    public abstract List<UserOutDto> map(List<User> user);

    public abstract UserBriefOutDto mapShort(User user);
}
