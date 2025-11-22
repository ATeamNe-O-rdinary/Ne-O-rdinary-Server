package org.ateam.ateam.domain.linker.repository;

import java.util.Optional;
import org.ateam.ateam.domain.linker.model.entity.Linker;
import org.ateam.ateam.domain.linker.model.enums.RateUnit;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LinkerRepository extends JpaRepository<Linker, Long> {

    boolean existsByMemberId(Long memberId);

    Optional<Linker> findByIdAndMemberId(Long id, Long memberId);


    @Query(value = "SELECT l FROM Linker l JOIN FETCH l.member " +
            "WHERE l.jobCategory = :categoryOfBusiness " +
            "AND l.calculatedMonthlyRate >= :minMonthlyRate " +
            "ORDER BY l.calculatedMonthlyRate DESC",
            countQuery = "SELECT count(l) FROM Linker l " +
                    "WHERE l.jobCategory = :categoryOfBusiness " +
                    "AND l.calculatedMonthlyRate >= :minMonthlyRate")
    Page<Linker> findByCategoryAndRateGreaterThan(
            @Param("categoryOfBusiness") CategoryOfBusiness categoryOfBusiness,
            @Param("minMonthlyRate") Integer minMonthlyRate,
            Pageable pageable
    );

    Optional<Linker> findByMemberId(Long memberId);

}
