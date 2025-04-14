package de.dlsa.api.services;

import de.dlsa.api.dtos.CategoryDto;
import de.dlsa.api.dtos.UserDto;
import de.dlsa.api.entities.*;
import de.dlsa.api.repositories.CategoryRepository;
import de.dlsa.api.repositories.SettingsRepository;
import de.dlsa.api.responses.ActionResponse;
import de.dlsa.api.responses.CategoryResponse;
import de.dlsa.api.responses.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryService(
            CategoryRepository categoryRepository,
            ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream()
                .sorted(Comparator.comparingLong(Category::getId))
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .collect(Collectors.toList());
    }

    public CategoryResponse createCategory(CategoryDto category) {

        Category mappedCategory = modelMapper.map(category, Category.class);

        Category addedCategory = categoryRepository.save(mappedCategory);

        return modelMapper.map(addedCategory, CategoryResponse.class);
    }

    public CategoryResponse updateCategory(long id, CategoryDto category) {

        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sparte wurde nicht gefunden!"));

        if (category.getCategoryName() != null) {
            existing.setCategoryName(category.getCategoryName());
        }

        Category updatedCategory =  categoryRepository.save(existing);

        return modelMapper.map(updatedCategory, CategoryResponse.class);

    }

    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }
}
