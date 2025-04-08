package de.dlsa.api.responses;

import de.dlsa.api.entities.Category;
import de.dlsa.api.entities.Member;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

public class CategoryResponse {
    private Long id;

    private String categoryName;

    public Long getId() {
        return id;
    }

    public CategoryResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public CategoryResponse setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }
}
