package de.dlsa.api.services;

import de.dlsa.api.dtos.SettingsDto;
import de.dlsa.api.entities.Settings;
import de.dlsa.api.repositories.SettingsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * Serviceklasse zur Verwaltung der globalen Systemeinstellungen.
 * Diese Klasse bietet Methoden zum Abrufen und Aktualisieren der gespeicherten Einstellungen.
 */
@Service
public class SettingsService {

    private final SettingsRepository settingsRepository;
    private final ModelMapper modelMapper;

    /**
     * Konstruktor zur Initialisierung des SettingsService.
     *
     * @param settingsRepository Repository für den Zugriff auf die {@link Settings}-Entität
     * @param modelMapper        ModelMapper zur Konvertierung von Objekten
     */
    public SettingsService(
            SettingsRepository settingsRepository,
            ModelMapper modelMapper) {
        this.settingsRepository = settingsRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Ruft die gespeicherten Systemeinstellungen ab.
     *
     * @return Das {@link Settings}-Objekt mit den aktuellen Einstellungen
     * @throws RuntimeException Wenn keine Einstellungen gefunden wurden
     */
    public Settings getSettings() {
        return settingsRepository.findOnlySettings()
                .orElseThrow(() -> new RuntimeException("Keine Einstellungen gefunden!"));
    }

    /**
     * Aktualisiert die gespeicherten Systemeinstellungen.
     *
     * @param id       Die ID der zu aktualisierenden Einstellungen
     * @param settings Ein {@link Settings}-Objekt mit den neuen Werten
     * @return Das aktualisierte {@link Settings}-Objekt
     * @throws RuntimeException Wenn keine Einstellung mit der übergebenen ID gefunden wurde
     */
    public Settings updateSettings(long id, SettingsDto settings) {

        Settings existing = settingsRepository.findById(id).orElseThrow(() -> new RuntimeException("Einstellung wurde nicht gefunden!"));

        // Alle Felder werden auf Grundlage der neuen Einstellungen überschrieben
        existing = modelMapper.map(settings, Settings.class);
        existing.setId(id);

        return settingsRepository.save(existing);
    }
}
