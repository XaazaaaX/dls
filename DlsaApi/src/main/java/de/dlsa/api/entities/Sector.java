package de.dlsa.api.entities;

import jakarta.persistence.*;
import java.util.Collection;

/**
 * Entität zur Repräsentation eines Bereichs innerhalb des Vereins (z. B. Abteilung, Region).
 * Jeder Bereich kann mehreren Gruppen zugeordnet sein.
 *
 * Die Zuordnung erfolgt über eine Many-to-Many-Beziehung zur {@link Group}-Entität.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@Entity
@Table(name = "Bereiche")
public class Sector extends BaseEntity {

    /**
     * Eindeutiger Name des Bereichs (z. B. "Jugend", "Verwaltung").
     */
    @Column(name = "Name", unique = true)
    private String sectorname;

    /**
     * Verknüpfte Gruppen, die diesem Bereich zugeordnet sind.
     * Many-to-Many über Join-Tabelle "bereiche_gruppen".
     */
    @ManyToMany
    @JoinTable(
            name = "bereiche_gruppen",
            joinColumns = @JoinColumn(name = "bereiche_id"),
            inverseJoinColumns = @JoinColumn(name = "groups_id") // optional, aber empfehlenswert
    )
    private Collection<Group> groups;

    // ===== Getter & Setter (Fluent API) =====

    public String getSectorname() {
        return sectorname;
    }

    public Sector setSectorname(String sectorname) {
        this.sectorname = sectorname;
        return this;
    }

    public Collection<Group> getGroups() {
        return groups;
    }

    public Sector setGroups(Collection<Group> groups) {
        this.groups = groups;
        return this;
    }
}
