package de.dlsa.api.repositories;

import de.dlsa.api.entities.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
    @Query("SELECT e FROM Settings e")
    Optional<Settings> findOnlySettings();
}

