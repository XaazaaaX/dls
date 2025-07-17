package de.dlsa.api.entities;

import jakarta.persistence.*;

/**
 * Entität zur Repräsentation eines Jahres.
 * Dient typischerweise als Referenzwert für zeitlich abgegrenzte Vorgänge: Auswertungen der Jahresläufe.
 *
 * Die Jahre werden getrennt gespeichert, um konsistente Jahresbezüge in der Datenbank zu ermöglichen.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@Entity
@Table(name = "jahre")
public class Year extends BaseEntity {

    /**
     * Repräsentiert ein Kalenderjahr (z. B. 2024).
     */
    @Column(name = "jahr")
    private int year;

    // ===== Getter & Setter (Fluent API) =====

    public int getYear() {
        return year;
    }

    public Year setYear(int year) {
        this.year = year;
        return this;
    }
}
