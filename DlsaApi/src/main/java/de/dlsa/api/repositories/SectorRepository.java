package de.dlsa.api.repositories;

import de.dlsa.api.entities.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository zur Verwaltung von {@link Sector}-Entitäten.
 * Ermöglicht CRUD-Zugriffe auf gespeicherte Bereiche (Sektoren).
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public interface SectorRepository extends JpaRepository<Sector, Long> {
    // Es sind derzeit keine benutzerdefinierten Abfragen notwendig.
}
