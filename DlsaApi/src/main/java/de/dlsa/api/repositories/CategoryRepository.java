package de.dlsa.api.repositories;

import de.dlsa.api.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository-Schnittstelle zur Verwaltung von {@link Category}-Entitäten.
 * Ermöglicht CRUD-Operationen sowie die Suche nach Spartenname.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Sucht eine Kategorie anhand ihres Namens.
     *
     * @param categoryName Name der Sparte
     * @return Optional mit Kategorie, falls vorhanden
     */
    Optional<Category> findByCategoryName(String categoryName);
}
