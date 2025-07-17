package de.dlsa.api.controllers;

import de.dlsa.api.responses.GroupChangesResponse;
import de.dlsa.api.responses.MemberChangesResponse;
import de.dlsa.api.services.HistorieService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Verarbeitung von Anfragen zur Anzeige von Änderungsverläufen (Historie)
 * für Gruppen und Mitglieder.
 *
 * Zugriff erlaubt für: Administrator, Benutzer.
 *
 * Basis-URL: /historie
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@RestController
@RequestMapping("/historie")
public class HistorieController {

    private final HistorieService historieService;

    /**
     * Konstruktor
     *
     * @param historieService Service zur Bereitstellung der Änderungsverläufe
     */
    public HistorieController(HistorieService historieService) {
        this.historieService = historieService;
    }

    /**
     * Endpunkt zur Abfrage von Gruppenänderungen
     *
     * Erlaubt für Rollen: Administrator, Benutzer
     *
     * @return Liste der dokumentierten Änderungen an Gruppen
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @GetMapping("/groups")
    public ResponseEntity<List<GroupChangesResponse>> getGroupChanges() {
        return ResponseEntity.ok(historieService.getGroupChanges());
    }

    /**
     * Endpunkt zur Abfrage von Mitgliederänderungen
     *
     * Erlaubt für Rollen: Administrator, Benutzer
     *
     * @return Liste der dokumentierten Änderungen an Mitgliedern
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @GetMapping("/members")
    public ResponseEntity<List<MemberChangesResponse>> getMemberChanges() {
        return ResponseEntity.ok(historieService.getMemberChanges());
    }
}
