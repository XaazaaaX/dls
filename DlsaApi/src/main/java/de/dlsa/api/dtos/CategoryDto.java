package de.dlsa.api.dtos;

import jakarta.validation.constraints.NotBlank;

/**
 * Datenübertragungsobjekt zur Erstellung oder Aktualisierung einer Sparte (Kategorie).
 * Wird vom Client gesendet, z. B. in Formularen oder REST-Anfragen.
 *
 * Enthält nur den Namen der Kategorie. Die ID wird vom Server verwaltet.
 *
 * Setter verwenden Fluent API zur einfachen Verkettung von Methoden.
 *
 * Beispiel:
 * <pre>
 *     new CategoryDto().setCategoryName("Tennis");
 * </pre>
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public class CategoryDto {

    /**
     * Bezeichnung der Sparte.
     * Darf nicht leer oder null sein.
     */
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