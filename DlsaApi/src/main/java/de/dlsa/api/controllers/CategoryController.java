package de.dlsa.api.controllers;

import de.dlsa.api.dtos.CategoryDto;
import de.dlsa.api.dtos.UserDto;
import de.dlsa.api.entities.Category;
import de.dlsa.api.entities.Settings;
import de.dlsa.api.entities.User;
import de.dlsa.api.responses.CategoryResponse;
import de.dlsa.api.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/categories")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @PostMapping
    public ResponseEntity<List<CategoryResponse>> createCategories(@RequestBody List<CategoryDto> categories) {
        List<CategoryResponse> created = categoryService.createCategories(categories);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable long id, @RequestBody CategoryDto category) {
        CategoryResponse updated = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(updated);
    }
}
