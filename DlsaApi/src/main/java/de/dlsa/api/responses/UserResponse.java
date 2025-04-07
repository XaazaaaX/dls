package de.dlsa.api.responses;

import de.dlsa.api.entities.Role;
import de.dlsa.api.entities.User;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * Klasse zur Darstellung einer User Response bei Rückgabe aus der Level-Anfrage des UserControllers
 *
 * @author Benito Ernst
 * @version  01/2024
 */
public class UserResponse {
    private Integer id;

    private String username;

    private boolean active;

    private Role role;

    /**
     * Getter für das User-Id
     *
     * @return Gibt den Wert aus dem Attribut "id" zurück
     */
    public Integer getId() {
        return id;
    }

    /**
     * Getter für den Username
     *
     * @return Gibt den Wert aus dem Attribut "username" zurück
     */
    public String getUsername() {
        return username;
    }

    public UserResponse setId(Integer id) {
        this.id = id;
        return this;
    }
    /**
     * Setter für den Username
     *
     * @param username Name des Users
     * @return Gibt die aktuelle Instanz der Klasse zurück
     */
    public UserResponse setUsername(String username) {
        this.username = username;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public UserResponse setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public UserResponse setRole(Role role) {
        this.role = role;
        return this;
    }
}
