package de.dlsa.api.repositories;

import de.dlsa.api.entities.Category;
import de.dlsa.api.entities.Member;
import de.dlsa.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(String memberId);
}
