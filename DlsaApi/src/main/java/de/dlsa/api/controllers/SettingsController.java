package de.dlsa.api.controllers;

import de.dlsa.api.entities.Settings;
import de.dlsa.api.services.SettingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Verarbeitung von Anfragen zur Anzeige und Aktualisierung der Anwendungseinstellungen.
 *
 * Zugriff ausschließlich für Administratoren.
 *
 * Basis-URL: /settings
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@RequestMapping("/settings")
@RestController
public class SettingsController {

    private final SettingsService settingsService;

    /**
     * Konstruktor
     *
     * @param settingsService Service zur Verwaltung der Systemeinstellungen
     */
    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    /**
     * Endpunkt zur Abfrage der aktuellen Systemeinstellungen
     *
     * Erlaubt nur für Rolle: Administrator
     *
     * @return Aktuelle Einstellungen als {@link Settings}
     */
    @PreAuthorize("hasAuthority('Administrator')")
    @GetMapping
    public ResponseEntity<Settings> getSettings() {
        return ResponseEntity.ok(settingsService.getSettings());
    }

    /**
     * Endpunkt zum Aktualisieren der Systemeinstellungen
     *
     * Erlaubt nur für Rolle: Administrator
     *
     * @param id       ID der zu aktualisierenden Einstellungen
     * @param settings Neue Einstellungswerte als {@link Settings}
     * @return Aktualisierte Einstellungen als {@link Settings}
     */
    @PreAuthorize("hasAuthority('Administrator')")
    @PutMapping("/{id}")
    public ResponseEntity<Settings> updateUser(@PathVariable long id, @RequestBody Settings settings) {
        Settings updated = settingsService.updateSettings(id, settings);
        return ResponseEntity.ok(updated);
    }
}
