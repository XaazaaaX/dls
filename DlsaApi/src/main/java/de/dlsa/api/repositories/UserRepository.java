package de.dlsa.api.repositories;

import de.dlsa.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository zur Verwaltung von {@link User}-Entitäten.
 * Stellt Funktionen für CRUD-Operationen und benutzerdefinierte Abfragen bereit.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Sucht einen Benutzer anhand des Benutzernamens.
     *
     * @param username Benutzername des gesuchten Users
     * @return Optional mit Benutzer, falls vorhanden
     */
    Optional<User> findByUsername(String username);

}
