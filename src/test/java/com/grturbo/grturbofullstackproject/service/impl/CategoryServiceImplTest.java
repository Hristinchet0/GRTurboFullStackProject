package com.grturbo.grturbofullstackproject.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.grturbo.grturbofullstackproject.model.dto.CategoryDto;
import com.grturbo.grturbofullstackproject.model.entity.Category;
import com.grturbo.grturbofullstackproject.model.mapper.CategoryMapper;
import com.grturbo.grturbofullstackproject.repositority.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CategoryServiceImplTest {

    @MockBean
    private CategoryMapper categoryMapper;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @Test
    void testAddCategory() {
        Category category = new Category();
        category.setId(123L);
        category.setName("Name");
        when(categoryRepository.save(any())).thenReturn(category);

        Category category1 = new Category();
        category1.setId(123L);
        category1.setName("Name");
        when(categoryMapper.categoryDtoToCategoryEntity(any())).thenReturn(category1);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(123L);
        categoryDto.setName("Name");
        categoryServiceImpl.addCategory(categoryDto);
        verify(categoryRepository).save(any());
        verify(categoryMapper).categoryDtoToCategoryEntity(any());
        assertEquals(123L, categoryDto.getId().longValue());
        assertEquals("Name", categoryDto.getName());
    }

}

