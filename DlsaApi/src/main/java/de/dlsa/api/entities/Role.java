package de.dlsa.api.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Rollen")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(name = "rollenname")
    private String rolename;

    public String getRolename() {
        return rolename;
    }

    public Role setRolename(String rolename) {
        this.rolename = rolename;
        return this;
    }
}
