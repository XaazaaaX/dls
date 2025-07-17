package de.dlsa.api.responses;

/**
 * Diese Klasse stellt die Antwortstruktur für eine einzelne Sparte (Kategorie) dar.
 * Sie wird typischerweise in REST-Antworten verwendet.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public class CategoryResponse {

    /**
     * Die eindeutige ID der Sparte (vom Datenbank-Primärschlüssel abgeleitet).
     */
    private Long id;

    /**
     * Der Name bzw. die Bezeichnung der Sparte.
     */
    private String categoryName;

    // ----- Getter & Setter im Fluent-Style -----

    /**
     * Gibt die ID der Sparte zurück.
     * @return die ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Setzt die ID der Sparte.
     * @param id die neue ID
     * @return die aktuelle Instanz
     */
    public CategoryResponse setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Gibt die Spartenbezeichnung zurück.
     * @return der Spartenname
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Setzt den Namen der Sparte.
     * @param categoryName der neue Spartenname
     * @return die aktuelle Instanz
     */
    public CategoryResponse setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }
}
