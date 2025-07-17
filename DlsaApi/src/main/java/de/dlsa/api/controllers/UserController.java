package de.dlsa.api.controllers;

import de.dlsa.api.dtos.UserDto;
import de.dlsa.api.responses.UserResponse;
import de.dlsa.api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Verarbeitung von Anfragen zur Verwaltung von Benutzern.
 *
 * Alle Endpunkte sind ausschließlich für Administratoren zugänglich.
 *
 * Basis-Endpunkte: /users, /user
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@RestController
public class UserController {

    private final UserService userService;

    /**
     * Konstruktor
     *
     * @param userService Service zur Verwaltung von Benutzerkonten
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpunkt zur Abfrage aller Benutzerkonten
     *
     * Erlaubt für Rolle: Administrator
     *
     * @return Liste aller Benutzer als {@link UserResponse}
     */
    @PreAuthorize("hasAuthority('Administrator')")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Endpunkt zur Erstellung eines neuen Benutzers
     *
     * Erlaubt für Rolle: Administrator
     *
     * @param user Neue Benutzerdaten als {@link UserDto} (validiert)
     * @return Erstellter Benutzer als {@link UserResponse}
     */
    @PreAuthorize("hasAuthority('Administrator')")
    @PostMapping("/user")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserDto user) {
        UserResponse created = userService.createUser(user);
        return ResponseEntity.ok(created);
    }

    /**
     * Endpunkt zum Aktualisieren eines bestehenden Benutzers
     *
     * Erlaubt für Rolle: Administrator
     *
     * @param id          ID des zu aktualisierenden Benutzers
     * @param userDetails Neue Benutzerdaten als {@link UserDto}
     * @return Aktualisierter Benutzer als {@link UserResponse}
     */
    @PreAuthorize("hasAuthority('Administrator')")
    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable long id, @RequestBody UserDto userDetails) {
        UserResponse updated = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updated);
    }

    /**
     * Endpunkt zum Löschen eines Benutzers
     *
     * Erlaubt für Rolle: Administrator
     *
     * @param id ID des zu löschenden Benutzers
     * @return Leere Antwort mit Status 204 (No Content)
     */
    @PreAuthorize("hasAuthority('Administrator')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
