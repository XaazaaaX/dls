package de.dlsa.api.repositories;

import de.dlsa.api.entities.GroupChanges;
import de.dlsa.api.entities.MemberChanges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface GroupChangesRepository extends JpaRepository<GroupChanges, Long> {
    @Query("SELECT c FROM GroupChanges c WHERE c.groupId = :id AND c.refDate <= :targetDate ORDER BY c.refDate ASC")
    List<GroupChanges> findChangesUpToDate(
            @Param("id") long groupId,
            @Param("targetDate") LocalDateTime targetDate
    );
}
