package de.dlsa.api.repositories;

import de.dlsa.api.entities.CourseOfYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

/**
 * Repository zur Verwaltung von {@link CourseOfYear}-Entitäten (Jahresläufe).
 * Bietet Methoden zum Zugriff auf Stichtage vergangener Jahresläufe.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public interface CourseOfYearRepository extends JpaRepository<CourseOfYear, Long> {

    /**
     * Gibt das jüngste (neueste) Stichtagsdatum zurück, das in einem Jahreslauf gespeichert wurde.
     *
     * @return Datum des letzten bekannten Stichtags oder {@code null}, wenn keine vorhanden sind
     */
    @Query("SELECT MAX(j.dueDate) FROM CourseOfYear j")
    LocalDateTime findLastDoneDate();
}
