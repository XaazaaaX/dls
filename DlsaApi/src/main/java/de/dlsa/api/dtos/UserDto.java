package de.dlsa.api.dtos;

import de.dlsa.api.entities.Role;
import de.dlsa.api.entities.User;
import de.dlsa.api.responses.UserResponse;

public class UserDto {

    private String username;

    private String password;

    private boolean active;

    private Role role;

    public String getUsername() {
        return username;
    }

    public UserDto setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Getter für das User-Passwort
     *
     * @return Gibt den Wert aus dem Attribut "password" zurück
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter für das User-Level
     *
     * @param password Passwort
     * @return Gibt die aktuelle Instanz der Klasse zurück
     */
    public UserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public UserDto setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public UserDto setRole(Role role) {
        this.role = role;
        return this;
    }
}
