package de.dlsa.api.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entität zur Protokollierung von Änderungen an Mitgliedsdaten.
 * Speichert, welche Spalte (Feld) eines Mitglieds geändert wurde, inklusive alter und neuer Werte,
 * Änderungszeitpunkt und zugehöriger Mitglieds-ID.
 *
 * Diese Klasse unterstützt eine revisionssichere Nachvollziehbarkeit historischer Zustände.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@Entity
@Table(name = "memberchanges")
public class MemberChanges extends BaseEntity {

    /**
     * Zeitstempel, wann die Änderung erfasst wurde.
     * Standardmäßig beim Objektbau mit dem aktuellen Zeitpunkt vorbelegt.
     */
    @Column(name = "timestamp")
    private LocalDateTime timestamp = LocalDateTime.now();

    /**
     * Bezugsdatum, das die inhaltliche Gültigkeit der Änderung beschreibt (z. B. Stichtag).
     */
    @Column(name = "bezugsdatum")
    private LocalDateTime refDate;

    /**
     * Name der geänderten Spalte (z. B. "active", "entryDate").
     */
    @Column(name = "spaltenname")
    private String column;

    /**
     * Alter (vorheriger) Wert als String.
     */
    @Column(name = "alterwert")
    private String oldValue;

    /**
     * Neuer (nach der Änderung gespeicherter) Wert als String.
     */
    @Column(name = "neuerwert")
    private String newValue;

    /**
     * ID des betroffenen Mitglieds.
     * Hinweis: keine direkte Entity-Relation (z. B. @ManyToOne), da rein für Audit-Zwecke.
     */
    @Column(name = "memberid")
    private Long memberId;

    // ===== Getter & Setter (Fluent API) =====

    public LocalDateTime getRefDate() {
        return refDate;
    }

    public MemberChanges setRefDate(LocalDateTime refDate) {
        this.refDate = refDate;
        return this;
    }

    public String getColumn() {
        return column;
    }

    public MemberChanges setColumn(String column) {
        this.column = column;
        return this;
    }

    public String getOldValue() {
        return oldValue;
    }

    public MemberChanges setOldValue(String oldValue) {
        this.oldValue = oldValue;
        return this;
    }

    public String getNewValue() {
        return newValue;
    }

    public MemberChanges setNewValue(String newValue) {
        this.newValue = newValue;
        return this;
    }

    public Long getMemberId() {
        return memberId;
    }

    public MemberChanges setMemberId(Long memberId) {
        this.memberId = memberId;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public MemberChanges setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
