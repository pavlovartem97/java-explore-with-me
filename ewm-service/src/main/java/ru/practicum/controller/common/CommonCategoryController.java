package ru.practicum.controller.common;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.CategoryOutDto;
import ru.practicum.service.common.CommonCategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Validated
public class CommonCategoryController {

    private final CommonCategoryService commonCategoryService;

    @GetMapping
    public List<CategoryOutDto> getCategories(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                              @RequestParam(defaultValue = "10") @Positive int size) {
        return commonCategoryService.getCategories(from, size);
    }

    @GetMapping("{catId}")
    public CategoryOutDto getCategory(@PathVariable @Positive long catId) {
        return commonCategoryService.getCategory(catId);
    }

}
