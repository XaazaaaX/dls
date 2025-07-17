package de.dlsa.api.dtos;

import jakarta.validation.constraints.NotBlank;

/**
 * Klasse zur Übertragung der Logindaten vom Client zum Server
 *
 * @author Benito Ernst
 * @version  05/2025
 */
public class LoginUserDto {
    @NotBlank(message = "Benutzername ist erforderlich")
    private String username;
    @NotBlank(message = "Passwort ist erforderlich")
    private String password;

    /**
     * Getter für den Username
     *
     * @return Gibt den Wert aus dem Attribut "username" zurück
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter für das Passwort
     *
     * @return Gibt den Wert aus dem Attribut "password" zurück
     */
    public String getPassword() {
        return password;
    }
}