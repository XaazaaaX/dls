package de.dlsa.api.repositories;

import de.dlsa.api.entities.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, Long> {
    //Optional<Category> findByCategoryName(String memberId);
}

