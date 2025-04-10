package de.dlsa.api.repositories;

import de.dlsa.api.entities.Category;
import de.dlsa.api.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    //Optional<Category> findByCategoryName(String memberId);
}
