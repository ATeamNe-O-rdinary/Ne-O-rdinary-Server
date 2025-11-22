package org.ateam.ateam.domain.linker.repository;

import java.util.Optional;
import org.ateam.ateam.domain.linker.model.entity.Linker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkerRepository extends JpaRepository<Linker, Long> {

	boolean existsByMemberId(Long memberId);

	Optional<Linker> findByIdAndMemberId(Long id, Long memberId);

	Optional<Linker> findByMemberId(Long memberId);
}
