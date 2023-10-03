package ru.practicum.service.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.category.CategoryOutDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.model.Category;
import ru.practicum.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonCategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryOutDto> getCategories(int from, int size) {
        Page<Category> categories = categoryRepository.findAll(PageRequest.of(from / size, size, Sort.by("id")));
        return categoryMapper.map(categories.getContent());
    }

    @Transactional(readOnly = true)
    public CategoryOutDto getCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        return categoryMapper.map(category);
    }
}
