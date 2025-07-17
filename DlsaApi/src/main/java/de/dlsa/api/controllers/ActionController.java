package de.dlsa.api.controllers;

import de.dlsa.api.dtos.ActionDto;
import de.dlsa.api.responses.ActionResponse;
import de.dlsa.api.services.ActionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Verarbeitung von Anfragen zur Erstellung, Bearbeitung und Abfrage von Aktionen.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@RestController
public class ActionController {

    private final ActionService actionService;

    /**
     * Konstruktor
     *
     * @param actionService Service zur Verarbeitung der Aktionsdaten
     */
    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    /**
     * Endpunkt zur Abfrage aller Aktionen
     *
     * Erlaubt für Rollen: Administrator, Benutzer
     *
     * @return Liste aller Aktionen als ActionResponse
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @GetMapping("/actions")
    public ResponseEntity<List<ActionResponse>> getActions() {
        return ResponseEntity.ok(actionService.getActions());
    }

    /**
     * Endpunkt zum Erstellen einer neuen Aktion
     *
     * Erlaubt für Rollen: Administrator, Benutzer
     *
     * @param action Aktionsdaten als ActionDto (validiert)
     * @return Die erstellte Aktion als ActionResponse
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @PostMapping("/action")
    public ResponseEntity<ActionResponse> createAction(@Valid @RequestBody ActionDto action) {
        ActionResponse created = actionService.createAction(action);
        return ResponseEntity.ok(created);
    }

    /**
     * Endpunkt zum Aktualisieren einer bestehenden Aktion anhand der ID
     *
     * Erlaubt für Rollen: Administrator, Benutzer
     *
     * @param id ID der zu aktualisierenden Aktion
     * @param action Neue Daten für die Aktion als ActionDto
     * @return Die aktualisierte Aktion als ActionResponse
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @PutMapping("/actions/{id}")
    public ResponseEntity<ActionResponse> updateAction(@PathVariable long id, @RequestBody ActionDto action) {
        ActionResponse updated = actionService.updateAction(id, action);
        return ResponseEntity.ok(updated);
    }
}
