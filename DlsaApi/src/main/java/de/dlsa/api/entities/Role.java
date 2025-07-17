package de.dlsa.api.entities;

import jakarta.persistence.*;

/**
 * Entität zur Repräsentation einer Benutzerrolle innerhalb der Anwendung.
 * Rollen steuern den Zugriff auf autorisierte Endpunkte (z. B. "Administrator", "Benutzer", "Gast").
 *
 * Wird in der Regel im Rahmen der Authentifizierung/Autorisierung verwendet.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@Entity
@Table(name = "Rollen")
public class Role extends BaseEntity {

    /**
     * Bezeichnung der Rolle, z. B. "Administrator".
     */
    @Column(name = "rollenname")
    private String rolename;

    // ===== Getter & Setter (Fluent API) =====

    public String getRolename() {
        return rolename;
    }

    public Role setRolename(String rolename) {
        this.rolename = rolename;
        return this;
    }
}
