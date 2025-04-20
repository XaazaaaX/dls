package de.dlsa.api.entities;

import jakarta.persistence.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;

@Table(name = "Gruppen")
@Entity
public class Group extends BaseEntity{

    @Column(name = "gruppenname", unique = true)
    private String groupName;

    @Column(name = "befreit")
    private Boolean liberated = false;

    @ManyToMany(mappedBy = "groups")
    private Collection<Member> member = new ArrayList<Member>();

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
}
