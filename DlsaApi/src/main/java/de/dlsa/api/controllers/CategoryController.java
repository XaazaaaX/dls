package de.dlsa.api.controllers;

import de.dlsa.api.dtos.CategoryDto;
import de.dlsa.api.responses.CategoryResponse;
import de.dlsa.api.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Verarbeitung von Anfragen zur Verwaltung und Anzeige von Sparten
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Konstruktor
     *
     * @param categoryService Service zur Weiterverarbeitung der Kategorien-Anfragen
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Endpunkt zur Abfrage aller vorhandenen Sparten
     *
     * Erlaubt für Rollen: Administrator, Benutzer, Gast
     *
     * @return Liste aller Kategorien als CategoryResponse
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer', 'Gast')")
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    /**
     * Endpunkt zum Erstellen einer neuen Sparten
     *
     * Erlaubt für Rollen: Administrator, Benutzer
     *
     * @param category Kategorie-Daten als CategoryDto (validiert)
     * @return Die erstellte Kategorie als CategoryResponse
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @PostMapping("/category")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryDto category) {
        CategoryResponse created = categoryService.createCategory(category);
        return ResponseEntity.ok(created);
    }

    /**
     * Endpunkt zum Aktualisieren einer bestehenden Sparte
     *
     * Erlaubt für Rollen: Administrator, Benutzer
     *
     * @param id       ID der zu aktualisierenden Kategorie
     * @param category Neue Kategorie-Daten als CategoryDto
     * @return Die aktualisierte Kategorie als CategoryResponse
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable long id, @RequestBody CategoryDto category) {
        CategoryResponse updated = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(updated);
    }

    /**
     * Endpunkt zum Löschen einer Sparte
     *
     * Erlaubt für Rolle: Administrator, Benutzer
     *
     * @param id ID des zu löschenden Sparte
     * @return Leere Antwort mit Status 204 (No Content)
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @DeleteMapping("/category/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
