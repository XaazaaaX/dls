package de.dlsa.api.repositories;

import de.dlsa.api.entities.Category;
import de.dlsa.api.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryName(String memberId);
}
