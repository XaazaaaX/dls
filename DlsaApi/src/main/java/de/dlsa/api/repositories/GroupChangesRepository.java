package de.dlsa.api.repositories;

import de.dlsa.api.entities.GroupChanges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository zur Verwaltung von {@link GroupChanges}-Entitäten.
 * Ermöglicht Zugriff auf Änderungsverläufe von Gruppen zu bestimmten Zeitpunkten.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public interface GroupChangesRepository extends JpaRepository<GroupChanges, Long> {

    /**
     * Liefert alle Änderungen einer Gruppe bis zu einem bestimmten Stichtag.
     *
     * @param groupId    ID der Gruppe
     * @param targetDate Zielzeitpunkt, bis zu dem Änderungen berücksichtigt werden sollen
     * @return Liste aller relevanten {@link GroupChanges}, sortiert nach Bezugsdatum aufsteigend
     */
    @Query("SELECT c FROM GroupChanges c WHERE c.groupId = :id AND c.refDate <= :targetDate ORDER BY c.refDate ASC")
    List<GroupChanges> findChangesUpToDate(
            @Param("id") long groupId,
            @Param("targetDate") LocalDateTime targetDate
    );

    List<GroupChanges> findAllByGroupId(long id);
}
