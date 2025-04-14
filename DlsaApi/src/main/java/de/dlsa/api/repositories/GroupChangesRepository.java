package de.dlsa.api.repositories;

import de.dlsa.api.entities.GroupChanges;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupChangesRepository extends JpaRepository<GroupChanges, Long> {
}
