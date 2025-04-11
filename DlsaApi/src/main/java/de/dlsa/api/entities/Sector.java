package de.dlsa.api.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

@Table(name = "Bereiche")
@Entity
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(name = "Name")
    private String sectorname;

    @ManyToMany
    private Collection<Group> groups = new ArrayList<Group>();

    public Long getId() {
        return id;
    }

    public Sector setId(Long id){
        this.id = id;
        return this;
    }

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