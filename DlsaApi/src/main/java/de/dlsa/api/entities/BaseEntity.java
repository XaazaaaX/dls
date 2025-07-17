package de.dlsa.api.entities;

import jakarta.persistence.*;

/**
 * Abstrakte Basisklasse für alle Entitäten mit einer generierten ID.
 * Wird über {@code @MappedSuperclass} in abgeleitete Klassen eingebunden, sodass das ID-Feld dort verfügbar ist.
 *
 * Alle Entitäten, die von {@code BaseEntity} erben, erhalten automatisch:
 * - ein Long-Feld {@code id}
 * - eine generierte ID mit {@code GenerationType.AUTO}
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * Primärschlüssel-ID, automatisch generiert.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    // ===== Getter & Setter =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
