package com.grturbo.grturbofullstackproject.model.dto;

import javax.validation.constraints.NotEmpty;

public class CategoryDto {

    private Long id;

    @NotEmpty
    private String name;

    public CategoryDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
