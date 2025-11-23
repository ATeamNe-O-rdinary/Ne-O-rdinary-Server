package org.ateam.ateam.domain.linko.repository;

import java.util.List;
import java.util.Optional;
import org.ateam.ateam.domain.linko.model.Linko;
import org.ateam.ateam.domain.member.entity.member.Member;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LinkoRepository extends JpaRepository<Linko, Long> {

  Optional<Linko> findByMember(Member member);


  @Query(
      value =
          "SELECT l FROM Linko l JOIN FETCH l.member "
              + "WHERE l.categoryOfBusiness = :categoryOfBusiness "
              + "AND l.calculatedMonthlyRate >= :minMonthlyRate "
              + "ORDER BY l.calculatedMonthlyRate DESC",
      countQuery =
          "SELECT count(l) FROM Linko l "
              + "WHERE l.categoryOfBusiness = :categoryOfBusiness "
              + "AND l.calculatedMonthlyRate >= :minMonthlyRate")
  Page<Linko> findByCategoryAndRateGreaterThan(
      @Param("categoryOfBusiness") CategoryOfBusiness categoryOfBusiness,
      @Param("minMonthlyRate") Integer minMonthlyRate,
      Pageable pageable);

  Optional<Linko> findByMemberId(Long memberId);

  boolean existsByMember_Id(Long memberId);



  @Query(
      value =
          "SELECT l "
              + "FROM Linko l "
              + "LEFT JOIN Link k ON k.linko = l "
              + // Link의 linko 필드와 조인
              "WHERE l.categoryOfBusiness IN :categories "
              + "GROUP BY l "
              + // Linko 별로 그룹화하여 카운트
              "ORDER BY COUNT(k) DESC", // Link(k)의 개수 기준으로 내림차순 정렬
      countQuery =
          "SELECT COUNT(l) FROM Linko l WHERE l.categoryOfBusiness IN :categories") // 페이징을 위한 카운트
  // 쿼리 별도 지정
  Page<Linko> findByCategoryOfBusinessInOrderByLinkCountDesc(
      @Param("categories") List<CategoryOfBusiness> categories, Pageable pageable);

  @Query(
      "SELECT l FROM Linko l WHERE l.categoryOfBusiness IN :categories ORDER BY l.createdAt DESC")
  Page<Linko> findByCategoryOfBusinessInOrderByIdDesc(
      @Param("categories") List<CategoryOfBusiness> categories, Pageable pageable);
}
