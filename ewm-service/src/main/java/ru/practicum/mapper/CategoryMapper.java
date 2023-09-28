package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.dto.CategoryInDto;
import ru.practicum.dto.CategoryOutDto;
import ru.practicum.model.Category;

import java.util.List;

@Mapper
public abstract class CategoryMapper {
    @Mapping(target = "id", ignore = true)
    public abstract Category map(CategoryInDto dto);

    public abstract CategoryOutDto map(Category category);

    public abstract List<CategoryOutDto> map(List<Category> categories);
}
