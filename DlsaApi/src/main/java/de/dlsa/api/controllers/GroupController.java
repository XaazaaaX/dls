package de.dlsa.api.controllers;

import de.dlsa.api.dtos.GroupDto;
import de.dlsa.api.responses.GroupResponse;
import de.dlsa.api.services.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Verarbeitung von Anfragen zur Anzeige, Erstellung und Aktualisierung von Gruppen.
 *
 * Zugriff geregelt über Benutzerrollen (Administrator, Benutzer, Gast).
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@RestController
public class GroupController {

    private final GroupService groupService;

    /**
     * Konstruktor
     *
     * @param groupService Service zur Verarbeitung von Gruppenoperationen
     */
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * Endpunkt zur Abfrage aller verfügbaren Gruppen
     *
     * Erlaubt für Rollen: Administrator, Benutzer, Gast
     *
     * @return Liste aller Gruppen als GroupResponse
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer', 'Gast')")
    @GetMapping("/groups")
    public ResponseEntity<List<GroupResponse>> getGroups() {
        return ResponseEntity.ok(groupService.getGroups());
    }

    /**
     * Endpunkt zur Erstellung einer neuen Gruppe
     *
     * Erlaubt für Rollen: Administrator, Benutzer
     *
     * @param group Gruppendaten als GroupDto (validiert)
     * @return Die erstellte Gruppe als GroupResponse
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @PostMapping("/group")
    public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody GroupDto group) {
        GroupResponse created = groupService.createGroup(group);
        return ResponseEntity.ok(created);
    }

    /**
     * Endpunkt zur Aktualisierung einer bestehenden Gruppe
     *
     * Erlaubt für Rollen: Administrator, Benutzer
     *
     * @param id    ID der zu aktualisierenden Gruppe
     * @param group Neue Gruppendaten als GroupDto
     * @return Die aktualisierte Gruppe als GroupResponse
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @PutMapping("/groups/{id}")
    public ResponseEntity<GroupResponse> updateGroup(@PathVariable long id, @RequestBody GroupDto group) {
        GroupResponse updated = groupService.updateGroup(id, group);
        return ResponseEntity.ok(updated);
    }
}
