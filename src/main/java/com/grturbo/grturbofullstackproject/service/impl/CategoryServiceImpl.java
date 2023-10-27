package com.grturbo.grturbofullstackproject.service.impl;

import com.grturbo.grturbofullstackproject.model.dto.CategoryAddDto;
import com.grturbo.grturbofullstackproject.model.entity.Category;
import com.grturbo.grturbofullstackproject.model.mapper.CategoryMapper;
import com.grturbo.grturbofullstackproject.repositority.CategoryRepository;
import com.grturbo.grturbofullstackproject.service.CategoryService;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public void addCategory(CategoryAddDto categoryAddDto) {
        Category newCategory = categoryMapper.categoryDtoToCategoryEntity(categoryAddDto);
        categoryRepository.save(newCategory);
    }

    @Override
    public void removeCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<Category> findById(Category category) {
        return categoryRepository.findById(category.getId());
    }

    @Override
    public Optional<Category> findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

}
