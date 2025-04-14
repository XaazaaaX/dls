package de.dlsa.api.controllers;

import de.dlsa.api.dtos.CategoryDto;
import de.dlsa.api.responses.CategoryResponse;
import de.dlsa.api.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @PostMapping("/category")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryDto category) {
        CategoryResponse created = categoryService.createCategory(category);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable long id, @RequestBody CategoryDto category) {
        CategoryResponse updated = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(updated);
    }
}
