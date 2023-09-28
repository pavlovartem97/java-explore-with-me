package ru.practicum.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.UserInDto;
import ru.practicum.dto.UserOutDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.UserMapper;
import ru.practicum.model.User;
import ru.practicum.repository.UserRepository;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserOutDto createUser(UserInDto userInDto) {
        User user = userMapper.map(userInDto);
        user = userRepository.save(user);
        return userMapper.map(user);
    }

    @Transactional(readOnly = true)
    public List<UserOutDto> getUsers(@Nullable List<Long> ids, @PositiveOrZero int from, @Positive int size) {
        if (ids != null && !ids.isEmpty()) {
            return userMapper.map(userRepository.findByIdIn(ids));
        } else {
            Page<User> users = userRepository.findAll(PageRequest.of(from / size, size));
            return userMapper.map(users.getContent());
        }
    }

    @Transactional
    public void deleteUser(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.delete(user);
    }
}
