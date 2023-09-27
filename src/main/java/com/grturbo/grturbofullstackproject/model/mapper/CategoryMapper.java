package com.grturbo.grturbofullstackproject.model.mapper;

import com.grturbo.grturbofullstackproject.model.dto.CategoryAddDto;
import com.grturbo.grturbofullstackproject.model.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category categoryDtoToCategoryEntity(CategoryAddDto categoryAddDto);
}
