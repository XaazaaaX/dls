package de.dlsa.api.repositories;

import de.dlsa.api.entities.MemberChanges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository zur Verwaltung von {@link MemberChanges}-Entitäten.
 * Dient der Historisierung von Mitgliederänderungen bis zu einem bestimmten Stichtag.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public interface MemberChangesRepository extends JpaRepository<MemberChanges, Long> {

    /**
     * Ermittelt alle Änderungen eines Mitglieds bis zu einem angegebenen Stichtag.
     *
     * @param memberId   ID des Mitglieds
     * @param targetDate Stichtag (Änderungen bis zu diesem Datum)
     * @return Liste aller {@link MemberChanges} bis zum Zielzeitpunkt, sortiert nach Bezugsdatum
     */
    @Query("SELECT c FROM MemberChanges c WHERE c.memberId = :id AND c.refDate <= :targetDate ORDER BY c.refDate ASC")
    List<MemberChanges> findChangesUpToDate(
            @Param("id") long memberId,
            @Param("targetDate") LocalDateTime targetDate
    );
}
