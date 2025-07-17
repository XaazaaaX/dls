package de.dlsa.api.responses;

import java.time.LocalDateTime;

/**
 * Antwortstruktur zur Darstellung von Änderungen an Gruppenbefreiungen.
 * Enthält Informationen über das Änderungsdatum, den alten und neuen Wert sowie den Gruppennamen.
 *
 * @version 05/2025
 */
public class GroupChangesResponse {

    /** Eindeutige ID der Änderung. */
    private Long id;

    /** Bezugsdatum der Änderung. */
    private LocalDateTime refDate;

    /** Ursprünglicher Wert vor der Änderung. */
    private Boolean oldValue;

    /** Neuer Wert nach der Änderung. */
    private Boolean newValue;

    /** Bezeichnung der betroffenen Gruppe. */
    private String groupName;

    // --- Getter & Fluent Setter ---

    public Long getId() {
        return id;
    }

    public GroupChangesResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getRefDate() {
        return refDate;
    }

    public GroupChangesResponse setRefDate(LocalDateTime refDate) {
        this.refDate = refDate;
        return this;
    }

    public Boolean getOldValue() {
        return oldValue;
    }

    public GroupChangesResponse setOldValue(Boolean oldValue) {
        this.oldValue = oldValue;
        return this;
    }

    public Boolean getNewValue() {
        return newValue;
    }

    public GroupChangesResponse setNewValue(Boolean newValue) {
        this.newValue = newValue;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public GroupChangesResponse setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }
}
