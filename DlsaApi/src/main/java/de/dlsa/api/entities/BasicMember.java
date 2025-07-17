package de.dlsa.api.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entität zur Verwaltung von Mitglieds-Basisdaten wie Aktivstatus, Eintritts- und Austrittsdatum.
 * Wird typischerweise genutzt, um die Kerninformationen eines Mitglieds getrennt von erweiterten Daten
 * (z. B. Gruppenzugehörigkeit, Rollen, DLS) abzubilden. Hauptsächlich für Basiszustand für Versionierung.
 *
 * Verknüpft über eine 1:1-Beziehung mit {@link Member}.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@Entity
@Table(name = "basicmember")
public class BasicMember extends BaseEntity {

    /**
     * Gibt an, ob das Mitglied derzeit aktiv ist.
     * true = aktiv, false = inaktiv.
     */
    @Column(name = "aktiv")
    private Boolean active;

    /**
     * Datum des Eintritts in den Verein oder die Organisation.
     */
    @Column(name = "eintrittsdatum")
    private LocalDateTime entryDate;

    /**
     * Datum des Austritts aus dem Verein (optional).
     * Kann null sein, wenn das Mitglied noch aktiv ist.
     */
    @Column(name = "austrittsdatum")
    private LocalDateTime leavingDate;

    /**
     * Zugehöriges Mitgliedsobjekt.
     * 1:1-Verbindung mit {@link Member}.
     */
    @OneToOne
    private Member member;

    // ===== Getter & Setter (Fluent API) =====

    public Boolean getActive() {
        return active;
    }

    public BasicMember setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public BasicMember setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public LocalDateTime getLeavingDate() {
        return leavingDate;
    }

    public BasicMember setLeavingDate(LocalDateTime leavingDate) {
        this.leavingDate = leavingDate;
        return this;
    }

    public Member getMember() {
        return member;
    }

    public BasicMember setMember(Member member) {
        this.member = member;
        return this;
    }
}
