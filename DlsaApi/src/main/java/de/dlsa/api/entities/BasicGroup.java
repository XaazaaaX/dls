/*
package de.dlsa.api.entities;

import jakarta.persistence.*;

@Entity
public class BasicGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(name = "dlsBefreit")
    private Boolean liberate;

    @Column(name = "gruppenname")
    private String groupName;

    @OneToOne
    private Group group;

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
*/