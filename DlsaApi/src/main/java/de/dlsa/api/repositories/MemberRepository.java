package de.dlsa.api.repositories;

import de.dlsa.api.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository zur Verwaltung von {@link Member}-Entitäten.
 * Bietet Methoden zur Abfrage von Mitgliederstatistiken und einzelnen Datensätzen.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * Sucht ein Mitglied anhand der eindeutigen Mitgliedsnummer.
     *
     * @param memberId Die Mitgliedsnummer
     * @return Das gefundene {@link Member}-Objekt oder null
     */
    Member findByMemberId(String memberId);

    /**
     * Gibt alle Mitglieder zurück, bei denen das AIKZ-Flag (Arbeit in kürzester Zeit) aktiviert ist.
     *
     * @return Liste von Mitgliedern mit aktivem AIKZ-Status
     */
    List<Member> findByAikzTrue();

    /**
     * Zählt alle Mitglieder, bei denen das AIKZ-Flag gesetzt ist.
     *
     * @return Anzahl aller Mitglieder mit aktivem AIKZ
     */
    @Query("SELECT COUNT(m) FROM Member m WHERE m.aikz = true")
    long countAllMembers();

    /**
     * Zählt alle derzeit aktiven Mitglieder.
     *
     * @return Anzahl aktiver Mitglieder
     */
    @Query("SELECT COUNT(m) FROM Member m WHERE m.active = true")
    long countActiveMembers();

    /**
     * Zählt alle derzeit passiven Mitglieder.
     *
     * @return Anzahl passiver Mitglieder
     */
    @Query("SELECT COUNT(m) FROM Member m WHERE m.active = false")
    long countPassiveMembers();

    /**
     * Zählt alle Mitglieder, die innerhalb eines bestimmten Geburtszeitraums geboren wurden.
     *
     * @param startDate Oberes Grenzdatum (ältester akzeptierter Geburtstag)
     * @param endDate   Unteres Grenzdatum (jüngster akzeptierter Geburtstag)
     * @return Anzahl der Mitglieder innerhalb des Zeitraums
     */
    @Query("SELECT COUNT(m) FROM Member m WHERE m.birthdate <= :startDate AND m.birthdate >= :endDate")
    long countMembersBornBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
