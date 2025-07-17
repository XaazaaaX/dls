package de.dlsa.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Datenübertragungsobjekt zur Erstellung oder Bearbeitung einer Gruppe.
 * Wird vom Client z. B. beim Erstellen von Gruppenzuordnungen verwendet.
 *
 * Fluent API ermöglicht das einfache Setzen von Eigenschaften über Method Chaining.
 *
 * Beispiel:
 * <pre>
 *     new GroupDto()
 *         .setGroupName("Seniorengruppe")
 *         .setLiberated(true);
 * </pre>
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public class GroupDto {

    /**
     * Bezeichnung der Gruppe (z. B. „Jugend“, „Eltern“, „Senioren“).
     * Muss angegeben sein und darf nicht leer oder nur Leerzeichen enthalten.
     */
    @NotBlank(message = "Gruppenbezeichnung ist erforderlich")
    private String groupName;

    /**
     * Gibt an, ob Mitglieder dieser Gruppe von DLS (Dienstleistungsstunden) befreit sind.
     * Muss explizit gesetzt sein (true oder false).
     */
    @NotNull(message = "DLS-Befreit? ist erforderlich")
    private Boolean liberated;

    // ===== Getter & Setter (Fluent API) =====

    public String getGroupName() {
        return groupName;
    }

    public GroupDto setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public Boolean getLiberated() {
        return liberated;
    }

    public GroupDto setLiberated(Boolean liberated) {
        this.liberated = liberated;
        return this;
    }
}
