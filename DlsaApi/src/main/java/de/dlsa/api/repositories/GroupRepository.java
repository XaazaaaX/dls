package de.dlsa.api.repositories;

import de.dlsa.api.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository zur Verwaltung von {@link Group}-Entitäten.
 * Bietet Zugriff auf Gruppen anhand ihres Namens.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public interface GroupRepository extends JpaRepository<Group, Long> {

    /**
     * Sucht eine Gruppe anhand ihres Namens.
     *
     * @param name Gruppenname
     * @return Optional mit {@link Group}, falls vorhanden
     */
    Optional<Group> findByGroupName(String name);
}
