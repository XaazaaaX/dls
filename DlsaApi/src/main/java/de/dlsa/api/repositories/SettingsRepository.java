package de.dlsa.api.repositories;

import de.dlsa.api.entities.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

/**
 * Repository zur Verwaltung der {@link Settings}-Entität.
 * Da es nur einen einzigen Eintrag geben soll, bietet dieses Repository eine gezielte Zugriffsmethode.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public interface SettingsRepository extends JpaRepository<Settings, Long> {

    /**
     * Gibt die gespeicherten Systemeinstellungen zurück.
     * Erwartet, dass nur ein Datensatz existiert.
     *
     * @return Optional mit der gefundenen Settings-Instanz
     */
    @Query("SELECT e FROM Settings e")
    Optional<Settings> findOnlySettings();
}
