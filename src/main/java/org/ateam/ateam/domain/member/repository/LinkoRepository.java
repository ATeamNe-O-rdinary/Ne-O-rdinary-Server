package org.ateam.ateam.domain.member.repository;

import java.util.Optional;
import org.ateam.ateam.domain.member.entity.Linko;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkoRepository extends JpaRepository<Linko, Long> {
  Optional<Linko> findByMemberId(Long memberId);

  boolean existsByMemberId(Long memberId);
}
