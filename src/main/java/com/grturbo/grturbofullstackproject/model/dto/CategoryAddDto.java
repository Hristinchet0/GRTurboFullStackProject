package com.grturbo.grturbofullstackproject.model.dto;

import javax.validation.constraints.NotEmpty;

public class CategoryAddDto {

    private Long id;

    @NotEmpty
    private String name;

    public CategoryAddDto() {
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
