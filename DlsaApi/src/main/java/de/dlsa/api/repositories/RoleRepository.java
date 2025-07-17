package de.dlsa.api.repositories;

import de.dlsa.api.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository zur Verwaltung von {@link Role}-Entitäten.
 * Dient dem Zugriff auf Benutzerrollen.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Sucht eine Rolle anhand ihres Namens.
     *
     * @param rolename Der Rollenname (z. B. "Administrator", "Benutzer")
     * @return Optional mit der passenden {@link Role}, falls vorhanden
     */
    Optional<Role> findByRolename(String rolename);
}
