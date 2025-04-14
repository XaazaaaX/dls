package de.dlsa.api.dtos;

import de.dlsa.api.responses.CategoryResponse;
import jakarta.validation.constraints.NotBlank;

public class CategoryDto {
    @NotBlank(message = "Spartenbezeichnung ist erforderlich")
    private String categoryName;


    public String getCategoryName() {
        return categoryName;
    }

    public CategoryDto setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }
}
