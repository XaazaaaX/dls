package de.dlsa.api.controllers;

import de.dlsa.api.dtos.SectorDto;
import de.dlsa.api.responses.SectorResponse;
import de.dlsa.api.services.SectorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Verarbeitung von Anfragen zur Anzeige, Erstellung und Bearbeitung von Sektoren.
 *
 * Rollenbasierter Zugriff: Gäste können Sektoren anzeigen, Änderungen sind Administratoren und Benutzern vorbehalten.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@RestController
public class SectorController {

    private final SectorService sectorService;

    /**
     * Konstruktor
     *
     * @param sectorService Service zur Verwaltung von Sektoren
     */
    public SectorController(SectorService sectorService) {
        this.sectorService = sectorService;
    }

    /**
     * Endpunkt zur Abfrage aller verfügbaren Sektoren
     *
     * Erlaubt für Rollen: Administrator, Benutzer, Gast
     *
     * @return Liste aller Sektoren als SectorResponse
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer', 'Gast')")
    @GetMapping("/sectors")
    public ResponseEntity<List<SectorResponse>> getSectors() {
        return ResponseEntity.ok(sectorService.getSectors());
    }

    /**
     * Endpunkt zum Erstellen eines neuen Sektors
     *
     * Erlaubt für Rollen: Administrator, Benutzer
     *
     * @param sector Neue Sektor-Daten als SectorDto (validiert)
     * @return Der erstellte Sektor als SectorResponse
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @PostMapping("/sector")
    public ResponseEntity<SectorResponse> createSector(@Valid @RequestBody SectorDto sector) {
        SectorResponse created = sectorService.createSector(sector);
        return ResponseEntity.ok(created);
    }

    /**
     * Endpunkt zum Aktualisieren eines bestehenden Sektors
     *
     * Erlaubt für Rollen: Administrator, Benutzer
     *
     * @param id     ID des zu aktualisierenden Sektors
     * @param sector Neue Sektor-Daten als SectorDto
     * @return Der aktualisierte Sektor als SectorResponse
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @PutMapping("/sectors/{id}")
    public ResponseEntity<SectorResponse> updateSector(@PathVariable long id, @RequestBody SectorDto sector) {
        SectorResponse updated = sectorService.updateSector(id, sector);
        return ResponseEntity.ok(updated);
    }
}
