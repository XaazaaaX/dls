package de.dlsa.api.repositories;

import de.dlsa.api.entities.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectorRepository extends JpaRepository<Sector, Long> {
    //Optional<Category> findByCategoryName(String memberId);
}