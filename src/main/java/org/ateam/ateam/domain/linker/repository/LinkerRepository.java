package org.ateam.ateam.domain.linker.repository;

import java.util.List;
import java.util.Optional;
import org.ateam.ateam.domain.linker.model.entity.Linker;
import org.ateam.ateam.domain.linker.model.enums.RateUnit;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;
import org.ateam.ateam.domain.member.enums.LinkTingRole;
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

    @Query(value = "SELECT l " +
            "FROM Linker l " +
            "LEFT JOIN Link k ON k.linker = l " +   // Link의 linker 필드와 조인
            "WHERE l.jobCategory IN :categories " +
            "GROUP BY l " +                         // Linker 별로 그룹화하여 카운트
            "ORDER BY COUNT(k) DESC",               // Link(k)의 개수 기준으로 내림차순 정렬
            countQuery = "SELECT COUNT(l) FROM Linker l WHERE l.jobCategory IN :categories") // 페이징을 위한 카운트 쿼리 별도 지정
    Page<Linker> findByCategoryOfBusinessInOrderByLinkCountDesc(
            @Param("categories") List<CategoryOfBusiness> categories,
            Pageable pageable
    );

    @Query("SELECT l FROM Linker l WHERE l.jobCategory IN :categories ORDER BY l.createdAt DESC")
    Page<Linker> findByCategoryOfBusinessInOrderByIdDesc(
            @Param("categories") List<CategoryOfBusiness> categories,
            Pageable pageable
    );

}
