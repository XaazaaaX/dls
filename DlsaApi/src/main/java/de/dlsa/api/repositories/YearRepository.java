package de.dlsa.api.repositories;

import de.dlsa.api.entities.Year;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository zur Verwaltung von {@link Year}-Entitäten.
 * Bietet Standard-CRUD-Operationen und eine Methode zum Suchen eines Jahres.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public interface YearRepository extends JpaRepository<Year, Long> {

    /**
     * Sucht ein Jahr anhand des Zahlenwerts.
     *
     * @param year Jahr als Integer
     * @return Optional mit Year-Entität, falls vorhanden
     */
    Optional<Year> findByYear(int year);

}
