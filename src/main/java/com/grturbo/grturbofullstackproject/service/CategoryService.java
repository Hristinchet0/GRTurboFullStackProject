package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.dto.CategoryAddDto;
import com.grturbo.grturbofullstackproject.model.entity.Category;
import com.grturbo.grturbofullstackproject.model.mapper.CategoryMapper;
import com.grturbo.grturbofullstackproject.repositority.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public void addCategory(CategoryAddDto categoryAddDto) {
        Category newCategory = categoryMapper.categoryDtoToCategoryEntity(categoryAddDto);
        categoryRepository.save(newCategory);
    }

    public void removeCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
