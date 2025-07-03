package de.dlsa.api.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

@Table(name = "Bereiche")
@Entity
public class Sector extends BaseEntity {

    @Column(name = "Name", unique = true)
    private String sectorname;

    @ManyToMany
    @JoinTable(name = "bereiche_gruppen", joinColumns = @JoinColumn(name = "bereiche_id"))
    private Collection<Group> groups;

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