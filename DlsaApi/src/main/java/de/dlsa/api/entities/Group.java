package de.dlsa.api.entities;

import jakarta.persistence.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;

@Table(name = "Gruppen")
@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(name = "gruppenname")
    private String groupName;
    @Column(name = "befreit")
    private Boolean liberated = false;

    @ManyToMany(mappedBy = "groups")
    private Collection<Member> member = new ArrayList<Member>();

    @OneToOne
    private BasicGroup basicGroup;

    @Transient
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

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
        Boolean oldValue = this.liberated;
        this.liberated = liberated;
        changes.firePropertyChange("Liberated", oldValue,
                liberated);
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

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }

    public void removeAllPropertyChangeListeners() {
        for (PropertyChangeListener l : changes.getPropertyChangeListeners()) {
            changes.removePropertyChangeListener(l);
        }
    }
}
