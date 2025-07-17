package de.dlsa.api.responses;

import java.time.LocalDateTime;

/**
 * Antwortstruktur zur Darstellung von Änderungen an einem Mitglied.
 * Enthält Änderungszeitpunkt, betroffene Spalte und alte sowie neue Werte.
 *
 * @version 05/2025
 */
public class MemberChangesResponse {

    /** Eindeutige ID der Änderung. */
    private Long id;

    /** Zeitstempel der Änderung. */
    private LocalDateTime timestamp;

    /** Bezugsdatum, zu dem die Änderung wirksam wurde. */
    private LocalDateTime refDate;

    /** Name der betroffenen Spalte (z. B. „entryDate“). */
    private String column;

    /** Alter Wert der geänderten Spalte. */
    private String oldValue;

    /** Neuer Wert der geänderten Spalte. */
    private String newValue;

    /** Vollständiger Name des betroffenen Mitglieds. */
    private String memberName;

    // --- Getter & Fluent Setter ---

    public Long getId() {
        return id;
    }

    public MemberChangesResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getRefDate() {
        return refDate;
    }

    public MemberChangesResponse setRefDate(LocalDateTime refDate) {
        this.refDate = refDate;
        return this;
    }

    public String getColumn() {
        return column;
    }

    public MemberChangesResponse setColumn(String column) {
        this.column = column;
        return this;
    }

    public String getOldValue() {
        return oldValue;
    }

    public MemberChangesResponse setOldValue(String oldValue) {
        this.oldValue = oldValue;
        return this;
    }

    public String getNewValue() {
        return newValue;
    }

    public MemberChangesResponse setNewValue(String newValue) {
        this.newValue = newValue;
        return this;
    }

    public String getMemberName() {
        return memberName;
    }

    public MemberChangesResponse setMemberName(String memberName) {
        this.memberName = memberName;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public MemberChangesResponse setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
