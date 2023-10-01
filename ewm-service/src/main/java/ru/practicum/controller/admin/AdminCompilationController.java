package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.compilation.CompilationInDto;
import ru.practicum.dto.compilation.CompilationOutDto;
import ru.practicum.dto.compilation.CompilationUpdateInDto;
import ru.practicum.service.admin.AdminCompilationService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
@Validated
public class AdminCompilationController {

    private final AdminCompilationService adminCompilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationOutDto createCompilation(@RequestBody @Valid CompilationInDto dto) {
        return adminCompilationService.createCompilation(dto);
    }

    @DeleteMapping("{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable long compId) {
        adminCompilationService.deleteCompilation(compId);
    }

    @PatchMapping("{compId}")
    public CompilationOutDto updateCompilation(@RequestBody @Valid CompilationUpdateInDto dto,
                                               @PathVariable long compId) {
        return adminCompilationService.updateCompilation(dto, compId);
    }
}
