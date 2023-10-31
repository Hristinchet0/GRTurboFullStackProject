package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.dto.CategoryDto;
import com.grturbo.grturbofullstackproject.model.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getAllCategory();

    void addCategory(CategoryDto categoryDto);

    void removeCategoryById(Long id);

    Optional<Category> findById(Category category);

    Optional<Category> findCategoryById(Long id);

}
