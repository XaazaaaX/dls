package de.dlsa.api.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Repräsentiert eine Änderungsprotokollierung für Gruppen.
 * Speichert alte und neue Werte für ein bestimmtes Änderungsdatum,
 * z. B. bei Umstellungen von "DLS befreit" zu "nicht befreit".
 *
 * Diese Entität dient der Nachvollziehbarkeit historischer Zustände einer Gruppe.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@Entity
@Table(name = "groupchanges")
public class GroupChanges extends BaseEntity {

    /**
     * Bezugsdatum der Änderung – z. B. Stichtag, an dem der Wechsel wirksam wurde.
     */
    @Column(name = "bezugsdatum")
    private LocalDateTime refDate;

    /**
     * Vorheriger Zustand (z. B. DLS-Befreit: true/false).
     */
    @Column(name = "alterwert")
    private Boolean oldValue;

    /**
     * Neuer Zustand nach der Änderung.
     */
    @Column(name = "neuerwert")
    private Boolean newValue;

    /**
     * ID der betroffenen Gruppe (wird hier als Long gespeichert, nicht als Entity-Relation).
     */
    @Column(name = "groupid")
    private Long groupId;

    // ===== Getter & Setter (Fluent API) =====

    public LocalDateTime getRefDate() {
        return refDate;
    }

    public GroupChanges setRefDate(LocalDateTime refDate) {
        this.refDate = refDate;
        return this;
    }

    public Boolean getOldValue() {
        return oldValue;
    }

    public GroupChanges setOldValue(Boolean oldValue) {
        this.oldValue = oldValue;
        return this;
    }

    public Boolean getNewValue() {
        return newValue;
    }

    public GroupChanges setNewValue(Boolean newValue) {
        this.newValue = newValue;
        return this;
    }

    public Long getGroupId() {
        return groupId;
    }

    public GroupChanges setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }
}
