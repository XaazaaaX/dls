package de.dlsa.api.repositories;

import de.dlsa.api.entities.BasicGroup;
import de.dlsa.api.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository zur Verwaltung von {@link BasicGroup}-Entitäten.
 * Ermöglicht Zugriff auf gruppenspezifische Metadaten (z. B. DLS-Befreiung).
 *
 * Enthält eine benutzerdefinierte Methode zur Suche nach einer {@code BasicGroup}
 * anhand ihrer zugehörigen {@link Group}.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public interface BasicGroupRepository extends JpaRepository<BasicGroup, Long> {

    /**
     * Findet die {@link BasicGroup}, die mit der angegebenen {@link Group} verknüpft ist.
     *
     * @param group Die zugehörige Gruppenentität
     * @return Die passende BasicGroup, falls vorhanden
     */
    BasicGroup findBasicGroupByGroup(Group group);
}
