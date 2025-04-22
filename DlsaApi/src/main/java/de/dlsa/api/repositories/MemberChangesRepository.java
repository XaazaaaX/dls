package de.dlsa.api.repositories;

import de.dlsa.api.entities.GroupChanges;
import de.dlsa.api.entities.MemberChanges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MemberChangesRepository extends JpaRepository<MemberChanges, Long> {

    @Query("SELECT c FROM MemberChanges c WHERE c.memberId = :id AND c.refDate <= :targetDate ORDER BY c.refDate ASC")
    List<MemberChanges> findChangesUpToDate(
            @Param("id") long memberId,
            @Param("targetDate") LocalDateTime targetDate
    );
}
