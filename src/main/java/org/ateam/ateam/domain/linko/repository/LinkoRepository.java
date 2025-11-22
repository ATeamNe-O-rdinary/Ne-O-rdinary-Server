package org.ateam.ateam.domain.linko.repository;

import java.util.Optional;
import org.ateam.ateam.domain.linko.model.Linko;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LinkoRepository extends JpaRepository<Linko, Long> {
    Optional<Linko> findByMemberId(Long memberId);

    boolean existsByMemberId(Long memberId);

    @Query(value = "SELECT l FROM Linko l JOIN FETCH l.member " +
            "WHERE l.categoryOfBusiness = :categoryOfBusiness " +
            "AND l.calculatedMonthlyRate >= :minMonthlyRate " +
            "ORDER BY l.calculatedMonthlyRate DESC",
            countQuery = "SELECT count(l) FROM Linko l " +
                    "WHERE l.categoryOfBusiness = :categoryOfBusiness " +
                    "AND l.calculatedMonthlyRate >= :minMonthlyRate")
    Page<Linko> findByCategoryAndRateGreaterThan(
            @Param("categoryOfBusiness") CategoryOfBusiness categoryOfBusiness,
            @Param("minMonthlyRate") Integer minMonthlyRate,
            Pageable pageable
    );
    Optional<Linko> findByMember_Id(Long memberId);
    boolean existsByMember_Id(Long memberId);
}
