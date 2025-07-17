package de.dlsa.api.repositories;

import de.dlsa.api.entities.Booking;
import de.dlsa.api.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository zur Verwaltung von {@link Booking}-Entitäten.
 * Stellt Methoden zur Analyse und Aggregation von Dienstleistungsstunden (DLS) bereit.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Ermittelt die Summe der DLS eines Mitglieds innerhalb eines bestimmten Zeitraums.
     *
     * @param member   Das Mitglied
     * @param fromDate Startdatum
     * @param toDate   Enddatum
     * @return Gesamtsumme der DLS im Zeitraum
     */
    @Query("""
        SELECT COALESCE(SUM(b.countDls), 0)
        FROM Booking b
        WHERE b.member = :member AND b.doneDate BETWEEN :fromDate AND :toDate
    """)
    double getSumDlsByMemberAndDateRange(@Param("member") Member member,
                                         @Param("fromDate") LocalDateTime fromDate,
                                         @Param("toDate") LocalDateTime toDate);

    /**
     * Ermittelt die Gesamtanzahl der DLS pro Jahr ab einem bestimmten Jahr.
     *
     * @param startYear Startjahr
     * @return Liste von Arrays [Jahr, Summe DLS]
     */
    @Query("""
        SELECT YEAR(b.doneDate), SUM(b.countDls)
        FROM Booking b
        WHERE YEAR(b.doneDate) >= :startYear AND b.action IS NOT NULL
        GROUP BY YEAR(b.doneDate)
        ORDER BY YEAR(b.doneDate)
    """)
    List<Object[]> findServiceHoursPerYearFrom(@Param("startYear") int startYear);

    /**
     * Ermittelt die monatliche Verteilung der DLS in einem Zeitraum.
     *
     * @param startDate Startdatum
     * @param endDate   Enddatum
     * @return Liste von Arrays [Jahr, Monat, Summe DLS]
     */
    @Query("""
        SELECT EXTRACT(YEAR FROM b.doneDate), EXTRACT(MONTH FROM b.doneDate), SUM(b.countDls)
        FROM Booking b
        WHERE b.doneDate BETWEEN :startDate AND :endDate AND b.action IS NOT NULL
        GROUP BY EXTRACT(YEAR FROM b.doneDate), EXTRACT(MONTH FROM b.doneDate)
        ORDER BY EXTRACT(YEAR FROM b.doneDate) DESC, EXTRACT(MONTH FROM b.doneDate)
    """)
    List<Object[]> findMonthlyServiceHours(@Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);

    /**
     * Gibt die Top-5-Mitglieder mit der höchsten DLS-Leistung zurück.
     *
     * @return Liste von Arrays [Vorname, Nachname, Summe DLS]
     */
    @Query(value = """
        SELECT m.vorname, m.nachname, SUM(b.anzahldls) AS total
        FROM buchungen b
        JOIN mitglieder m ON m.id = b.member_id
        WHERE b.campaign_id IS NOT NULL
        GROUP BY m.vorname, m.nachname
        ORDER BY total DESC
        LIMIT 5
    """, nativeQuery = true)
    List<Object[]> findTop5DlsMembers();

    /**
     * Gibt die DLS-Summe pro Bereich (Sektor) eines Jahres zurück.
     *
     * @param year Jahr
     * @return Liste von Arrays [Bereichsname, Summe DLS]
     */
    @Query(value = """
        SELECT b2."name", COALESCE(SUM(b.anzahldls), 0)
        FROM bereiche b2
        LEFT JOIN bereiche_gruppen bg ON bg.bereiche_id = b2.id
        LEFT JOIN gruppen g ON g.id = bg.groups_id
        LEFT JOIN mitglieder_gruppen mg ON mg.groups_id = g.id
        LEFT JOIN mitglieder m ON m.id = mg.member_id
        LEFT JOIN buchungen b ON b.member_id = m.id
            AND EXTRACT(YEAR FROM b.ableistungsdatum) = :year
            AND b.campaign_id IS NOT NULL
        GROUP BY b2."name"
        ORDER BY b2."name"
    """, nativeQuery = true)
    List<Object[]> findSectorsWithDlsFromYear(@Param("year") int year);

    /**
     * Gibt die DLS-Summe pro Gruppe eines Jahres zurück.
     *
     * @param year Jahr
     * @return Liste von Arrays [Gruppenname, Summe DLS]
     */
    @Query(value = """
        SELECT g.gruppenname, COALESCE(SUM(b.anzahldls), 0)
        FROM gruppen g
        LEFT JOIN mitglieder_gruppen mg ON mg.groups_id = g.id
        LEFT JOIN mitglieder m ON m.id = mg.member_id
        LEFT JOIN buchungen b ON b.member_id = m.id
            AND EXTRACT(YEAR FROM b.ableistungsdatum) = :year
            AND b.campaign_id IS NOT NULL
        GROUP BY g.gruppenname
        ORDER BY g.gruppenname
    """, nativeQuery = true)
    List<Object[]> findGroupsWithDlsFromYear(@Param("year") int year);

    /**
     * Gibt die DLS-Summe pro Sparte (Kategorie) eines Jahres zurück.
     *
     * @param year Jahr
     * @return Liste von Arrays [Spartenname, Summe DLS]
     */
    @Query(value = """
        SELECT s.spartenname, COALESCE(SUM(b.anzahldls), 0)
        FROM sparten s
        LEFT JOIN mitglieder_sparten mc ON mc.categories_id = s.id
        LEFT JOIN mitglieder m ON m.id = mc.member_id
        LEFT JOIN buchungen b ON b.member_id = m.id
            AND EXTRACT(YEAR FROM b.ableistungsdatum) = :year
            AND b.campaign_id IS NOT NULL
        GROUP BY s.spartenname
        ORDER BY s.spartenname
    """, nativeQuery = true)
    List<Object[]> findCategoriesWithDlsFromYear(@Param("year") int year);
}
