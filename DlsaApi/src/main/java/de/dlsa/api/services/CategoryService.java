package de.dlsa.api.services;

import de.dlsa.api.dtos.CategoryDto;
import de.dlsa.api.entities.Category;
import de.dlsa.api.repositories.CategoryRepository;
import de.dlsa.api.responses.CategoryResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service zur Verwaltung von Sparten (Kategorien).
 * Bietet CRUD-Operationen für Kategorien.
 *
 * @version 05/2025
 */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    /**
     * Konstruktor mit benötigten Abhängigkeiten.
     */
    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Gibt alle vorhandenen Sparten sortiert nach ID zurück.
     *
     * @return Liste der Kategorien
     */
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream()
                .sorted(Comparator.comparingLong(Category::getId).reversed())
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Erstellt eine neue Sparte.
     *
     * @param category DTO mit Kategorieinformationen
     * @return Erstellte Sparte als Antwortobjekt
     */
    public CategoryResponse createCategory(CategoryDto category) {
        Category mappedCategory = modelMapper.map(category, Category.class);
        Category addedCategory = categoryRepository.save(mappedCategory);
        return modelMapper.map(addedCategory, CategoryResponse.class);
    }

    /**
     * Aktualisiert eine vorhandene Sparte anhand der ID.
     *
     * @param id       ID der zu ändernden Sparte
     * @param category Neue Werte
     * @return Aktualisierte Sparte
     * @throws RuntimeException Wenn die Sparte nicht gefunden wurde
     */
    public CategoryResponse updateCategory(long id, CategoryDto category) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sparte wurde nicht gefunden!"));

        if (category.getCategoryName() != null) {
            existing.setCategoryName(category.getCategoryName());
        }

        Category updatedCategory = categoryRepository.save(existing);
        return modelMapper.map(updatedCategory, CategoryResponse.class);
    }

    /**
     * Löscht eine Sparte anhand der ID.
     *
     * @param id ID der zu löschenden Sparte
     */
    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }
}
