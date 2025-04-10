package de.dlsa.api.dtos;

import de.dlsa.api.responses.CategoryResponse;

public class CategoryDto {

    private String categoryName;


    public String getCategoryName() {
        return categoryName;
    }

    public CategoryDto setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }
}
