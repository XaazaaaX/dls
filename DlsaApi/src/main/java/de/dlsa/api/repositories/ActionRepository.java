package de.dlsa.api.repositories;

import de.dlsa.api.entities.Action;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository-Schnittstelle zur Verwaltung von {@link Action}-Entitäten.
 * Ermöglicht CRUD-Operationen und Erweiterung um eigene Query-Methoden bei Bedarf.
 *
 * Wird von Spring Data JPA automatisch implementiert.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public interface ActionRepository extends JpaRepository<Action, Long> {
}
