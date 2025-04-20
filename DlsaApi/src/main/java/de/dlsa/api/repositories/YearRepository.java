package de.dlsa.api.repositories;

import de.dlsa.api.entities.Year;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YearRepository extends JpaRepository<Year, Long> {
    Optional<Year> findByYear(int year);
}


