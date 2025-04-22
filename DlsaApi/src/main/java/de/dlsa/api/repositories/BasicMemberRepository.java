package de.dlsa.api.repositories;

import de.dlsa.api.entities.BasicMember;
import de.dlsa.api.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicMemberRepository extends JpaRepository<BasicMember, Long> {
    BasicMember findBasicMemberByMember(Member member);
}
