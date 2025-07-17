package de.dlsa.api.dtos;

import de.dlsa.api.entities.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Datenübertragungsobjekt zur Erstellung oder Bearbeitung eines Benutzers.
 * Beinhaltet Benutzernamen, Passwort, Aktivstatus und Rolle.
 *
 * Diese Klasse wird z. B. bei Benutzerverwaltung, Login oder Rollenmanagement verwendet.
 * Setter sind als Fluent API implementiert.
 *
 * Beispiel:
 * <pre>
 *     new UserDto()
 *         .setUsername("admin")
 *         .setPassword("geheim")
 *         .setActive(true)
 *         .setRole(roleObject);
 * </pre>
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public class UserDto {

    /**
     * Benutzername (Pflichtfeld, darf nicht leer sein).
     */
    @NotBlank(message = "Benutzername ist erforderlich")
    private String username;

    /**
     * Passwort des Benutzers (Pflichtfeld).
     * Sollte im Backend verschlüsselt gespeichert werden.
     */
    @NotBlank(message = "Passwort ist erforderlich")
    private String password;

    /**
     * Gibt an, ob der Benutzer aktiv ist.
     * Pflichtfeld – true oder false muss angegeben werden.
     */
    @NotNull(message = "Aktiv? ist erforderlich")
    private boolean active;

    /**
     * Zugewiesene Rolle des Benutzers (z. B. Administrator, Benutzer, Gast).
     * Pflichtfeld.
     */
    @NotNull(message = "Rolle ist erforderlich")
    private Role role;

    // ===== Getter & Setter (Fluent API) =====

    public String getUsername() {
        return username;
    }

    public UserDto setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Passwort abrufen.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Passwort setzen.
     */
    public UserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Aktivstatus abrufen.
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Aktivstatus setzen.
     */
    public UserDto setActive(Boolean active) {
        this.active = active;
        return this;
    }

    /**
     * Rolle abrufen.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Rolle setzen.
     */
    public UserDto setRole(Role role) {
        this.role = role;
        return this;
    }
}
