package ru.practicum.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.CategoryInDto;
import ru.practicum.dto.CategoryOutDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.model.Category;
import ru.practicum.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class AdminCategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryOutDto createCategory(CategoryInDto dto) {
        Category category = categoryMapper.map(dto);
        category = categoryRepository.save(category);
        return categoryMapper.map(category);
    }

    @Transactional
    public CategoryOutDto updateCategory(long categoryId, CategoryInDto dto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        category.setName(dto.getName());
        return categoryMapper.map(category);
    }

    @Transactional(readOnly = true)
    public void deleteCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        categoryRepository.delete(category);
    }
}
