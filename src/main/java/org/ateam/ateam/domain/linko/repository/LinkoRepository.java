package org.ateam.ateam.domain.linko.repository;

import java.util.Optional;
import org.ateam.ateam.domain.linko.model.Linko;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkoRepository extends JpaRepository<Linko, Long> {
    Optional<Linko> findByMember_Id(Long memberId);
    boolean existsByMember_Id(Long memberId);
}