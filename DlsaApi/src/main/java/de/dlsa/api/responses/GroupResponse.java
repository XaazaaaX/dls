package de.dlsa.api.responses;

/**
 * Antwortstruktur zur Darstellung einer Gruppe.
 * Enthält Informationen wie ID, Name und Befreiungsstatus der Gruppe.
 *
 * @version 05/2025
 */
public class GroupResponse {

    /** Eindeutige ID der Gruppe. */
    private Long id;

    /** Bezeichnung der Gruppe. */
    private String groupName;

    /** Gibt an, ob die Gruppe DLS-befreit ist. */
    private Boolean liberated;

    // --- Getter & Fluent Setter ---

    public Long getId() {
        return id;
    }

    public GroupResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public GroupResponse setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public Boolean getLiberated() {
        return liberated;
    }

    public GroupResponse setLiberated(Boolean liberated) {
        this.liberated = liberated;
        return this;
    }
}
