package de.dlsa.api.repositories;

import de.dlsa.api.entities.BasicMember;
import de.dlsa.api.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository zur Verwaltung von {@link BasicMember}-Entitäten.
 * Dient dem Zugriff auf grundlegende Mitgliedsdaten wie Aktivstatus, Eintritts-/Austrittsdatum.
 *
 * Beinhaltet eine Methode zur Ermittlung eines {@link BasicMember} anhand des zugehörigen {@link Member}.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public interface BasicMemberRepository extends JpaRepository<BasicMember, Long> {

    /**
     * Findet die {@link BasicMember}-Entität, die dem angegebenen {@link Member} zugeordnet ist.
     *
     * @param member Die zugehörige Mitgliedsentität
     * @return Die passende BasicMember-Instanz oder {@code null}, falls nicht vorhanden
     */
    BasicMember findBasicMemberByMember(Member member);
}
