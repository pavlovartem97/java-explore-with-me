package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.UserInDto;
import ru.practicum.dto.UserOutDto;
import ru.practicum.service.AdminUserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@Validated
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping
    public List<UserOutDto> getUsers(@RequestParam(required = false) List<Long> ids,
                                     @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                     @RequestParam(defaultValue = "20") @Positive int size) {
        return adminUserService.getUsers(ids, from, size);
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable long userId) {
        adminUserService.deleteUser(userId);
    }

    @PostMapping
    public UserOutDto createUser(@RequestBody @Valid UserInDto dto) {
        return adminUserService.createUser(dto);
    }
}
