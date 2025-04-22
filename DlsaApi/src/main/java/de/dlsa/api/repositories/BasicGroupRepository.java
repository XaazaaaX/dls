package de.dlsa.api.repositories;

import de.dlsa.api.entities.BasicGroup;
import de.dlsa.api.entities.BasicMember;
import de.dlsa.api.entities.Group;
import de.dlsa.api.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicGroupRepository extends JpaRepository<BasicGroup, Long> {
    BasicGroup findBasicGroupByGroup(Group group);
}