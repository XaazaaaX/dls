package de.dlsa.api.entities;

import jakarta.persistence.*;

/**
 * Entität zur Abbildung einer grundlegenden Gruppendefinition.
 * Eine {@code BasicGroup} beschreibt eine Gruppe mit einem Namen und der Information,
 * ob Mitglieder dieser Gruppe von der DLS-Pflicht befreit sind.
 *
 * Diese Entität ist direkt einer konkreten {@link Group} zugeordnet (1:1-Beziehung).
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@Entity
@Table(name = "basicgroup")
public class BasicGroup extends BaseEntity {

    /**
     * Gibt an, ob die Gruppe von der Dienstleistungsstundenpflicht befreit ist.
     * true = befreit, false = nicht befreit.
     */
    @Column(name = "dlsBefreit")
    private Boolean liberate;

    /**
     * Der Name der Gruppe (z. B. "Jugend", "Eltern", "Verwaltung").
     */
    @Column(name = "gruppenname")
    private String groupName;

    /**
     * Referenz auf die zugehörige {@link Group}-Entität.
     * 1:1-Verknüpfung.
     */
    @OneToOne
    private Group group;

    // ===== Getter & Setter (Fluent API) =====

    public Boolean getLiberate() {
        return liberate;
    }

    public BasicGroup setLiberate(Boolean liberate) {
        this.liberate = liberate;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public BasicGroup setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public Group getGroup() {
        return group;
    }

    public BasicGroup setGroup(Group group) {
        this.group = group;
        return this;
    }
}
