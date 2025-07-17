package de.dlsa.api.entities;

import jakarta.persistence.*;

/**
 * Entität zur Abbildung einer Aktion innerhalb der Vereinsstruktur.
 * Eine Aktion ist z. B. ein Ereignis oder Projekt, das in einem bestimmten Jahr stattfindet
 * und einer Kontaktperson (Mitglied) zugewiesen ist.
 *
 * Diese Klasse ist per JPA mit der Datenbanktabelle "Aktionen" verbunden.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@Entity
@Table(name = "Aktionen")
public class Action extends BaseEntity {

    /**
     * Jahr, dem die Aktion zugeordnet ist (z. B. "2024").
     */
    @Column(name = "jahr")
    private String year;

    /**
     * Beschreibung der Aktion.
     * Muss eindeutig sein – {@code unique = true}.
     */
    @Column(name = "beschreibung", unique = true)
    private String description;

    /**
     * Verknüpfung zur Kontaktperson (Mitglied), das für die Aktion verantwortlich ist.
     */
    @ManyToOne
    private Member contact;

    // ===== Getter & Setter (Fluent API) =====

    public String getYear() {
        return year;
    }

    public Action setYear(String year) {
        this.year = year;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Action setDescription(String description) {
        this.description = description;
        return this;
    }

    public Member getContact() {
        return contact;
    }

    public Action setContact(Member contact) {
        this.contact = contact;
        return this;
    }
}
