package de.dlsa.api.repositories;

import de.dlsa.api.entities.Category;
import de.dlsa.api.entities.Member;
import de.dlsa.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByMemberId(String memberId);

    @Query("SELECT COUNT(m) FROM Member m WHERE m.aikz = true")
    long countAllMembers();

    @Query("SELECT COUNT(m) FROM Member m WHERE m.active = true")
    long countActiveMembers();

    @Query("SELECT COUNT(m) FROM Member m WHERE m.active = false")
    long countPassiveMembers();

    //@Query("SELECT COUNT(m) FROM Member m WHERE m.birthdate BETWEEN :startDate AND :endDate")
    @Query("SELECT COUNT(m) FROM Member m WHERE m.birthdate <= :startDate AND m.birthdate >= :endDate")
    long countMembersBornBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}