package ru.practicum.controller.common;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.compilation.CompilationOutDto;
import ru.practicum.service.common.CommonCompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
@Validated
public class CommonCompilationController {

    private final CommonCompilationService commonCompilationService;

    @GetMapping("/{compId}")
    public CompilationOutDto getCompilation(@PathVariable long compId) {
        return commonCompilationService.getCompilation(compId);
    }

    @GetMapping
    public List<CompilationOutDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                   @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                   @RequestParam(defaultValue = "10") @Positive int size) {
        return commonCompilationService.getCompilations(pinned, from, size);
    }
}
