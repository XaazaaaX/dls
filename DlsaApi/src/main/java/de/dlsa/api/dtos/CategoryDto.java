package de.dlsa.api.dtos;

import de.dlsa.api.responses.CategoryResponse;

public class CategoryDto {
    private Long id;

    private String categoryName;

    public Long getId() {
        return id;
    }

    public CategoryDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public CategoryDto setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }
}
