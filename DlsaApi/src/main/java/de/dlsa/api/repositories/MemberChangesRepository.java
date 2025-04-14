package de.dlsa.api.repositories;

import de.dlsa.api.entities.GroupChanges;
import de.dlsa.api.entities.MemberChanges;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberChangesRepository extends JpaRepository<MemberChanges, Long> {
}
