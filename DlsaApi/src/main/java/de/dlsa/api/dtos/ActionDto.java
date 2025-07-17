package de.dlsa.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * Datenübertragungsobjekt (DTO) zur Übergabe von Aktionsdaten zwischen Client und Server.
 * Wird für das Erstellen und Aktualisieren von Aktionen verwendet.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public class ActionDto {

    /**
     * Jahr, dem die Aktion zugeordnet ist.
     * Muss 1–4 Ziffern enthalten (z. B. "2024").
     */
    @NotBlank(message = "Jahr ist erforderlich")
    @Pattern(regexp = "^\\d{1,4}$", message = "Jahr darf nur 4 Zahlen besitzen")
    private String year;

    /**
     * Beschreibung der Aktion (z. B. "Jahreshauptversammlung").
     * Darf nicht leer sein.
     */
    @NotBlank(message = "Beschreibung ist erforderlich")
    private String description;

    /**
     * Referenz auf einen bestehenden Kontakt (Kontakt-ID).
     * Muss gesetzt sein.
     */
    @NotNull(message = "Kontakt ist erforderlich")
    private Long contactId;

    // Getter & Setter mit Fluent-API

    public String getYear() {
        return year;
    }

    public ActionDto setYear(String year) {
        this.year = year;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ActionDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getContactId() {
        return contactId;
    }

    public ActionDto setContactId(Long contactId) {
        this.contactId = contactId;
        return this;
    }
}
