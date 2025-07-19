package de.dlsa.api.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Repräsentiert eine organisatorische Gruppe innerhalb des Vereins (z. B. "Jugend", "Vorstand").
 * Eine Gruppe kann mehreren Mitgliedern zugeordnet sein und steht ggf. in Verbindung mit einer {@link BasicGroup}.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@Entity
@Table(name = "Gruppen")
public class Group extends BaseEntity {

    /**
     * Eindeutiger Name der Gruppe.
     */
    @Column(name = "gruppenname", unique = true)
    private String groupName;

    /**
     * Gibt an, ob die Gruppe pauschal von DLS befreit ist.
     * Standardwert ist false.
     */
    @Column(name = "befreit")
    private Boolean liberated = false;

    /**
     * 1:1-Verknüpfung zur zugehörigen {@link BasicGroup}.
     * Diese enthält ggf. weiterführende Metadaten zur Gruppe.
     */
    @OneToOne
    @JoinColumn(name = "basicgroup_id")
    private BasicGroup basicGroup;

    /**
     * Liste der Mitglieder, die dieser Gruppe zugeordnet sind.
     * Diese Seite ist die inverse Seite der Many-to-Many-Beziehung.
     */
    @ManyToMany(mappedBy = "groups")
    private Collection<Member> member = new ArrayList<>();

    // ===== Getter & Setter (Fluent API) =====

    public String getGroupName() {
        return groupName;
    }

    public Group setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public Boolean getLiberated() {
        return liberated;
    }

    public Group setLiberated(Boolean liberated) {
        this.liberated = liberated;
        return this;
    }

    public Collection<Member> getMember() {
        return member;
    }

    public Group setMember(Collection<Member> member) {
        this.member = member;
        return this;
    }

    public BasicGroup getBasicGroup() {
        return basicGroup;
    }

    public Group setBasicGroup(BasicGroup basicGroup) {
        this.basicGroup = basicGroup;
        return this;
    }
}
