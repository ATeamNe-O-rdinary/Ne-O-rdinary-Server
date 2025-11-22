package org.ateam.ateam.domain.member.repository;

import java.util.Optional;

import org.ateam.ateam.domain.linker.model.entity.Linker;
import org.ateam.ateam.domain.member.entity.Member;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;
import org.ateam.ateam.global.auth.enums.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

  /** 이메일로 회원 조회 */
  Optional<Member> findByEmail(String email);

  /** 이메일과 Provider로 회원 조회 */
  Optional<Member> findByEmailAndLoginProvider(String email, Provider loginProvider);

  /** 활성 회원만 조회 (탈퇴하지 않은 회원) */
  @Query("SELECT m FROM Member m WHERE m.email = :email AND m.deletedAt IS NULL")
  Optional<Member> findActiveByEmail(@Param("email") String email);

  /** ID로 활성 회원 조회 */
  @Query("SELECT m FROM Member m WHERE m.id = :id AND m.deletedAt IS NULL")
  Optional<Member> findActiveById(@Param("id") Long id);

  /** 이메일 존재 여부 확인 */
  boolean existsByEmail(String email);

  /** 활성 회원 수 조회 */
  @Query("SELECT COUNT(m) FROM Member m WHERE m.deletedAt IS NULL")
  long countActiveMembers();

  @Query(
      value =
          "SELECT m FROM Member m "
              + "JOIN FETCH m.spec s "
              + "WHERE s.categoryOfBusiness = :categoryOfBusiness "
              + "AND s.minSalary >= :minSalary "
              + "AND s.maxSalary <= :maxSalary",
      countQuery =
          "SELECT count(m) FROM Member m JOIN m.spec s "
              + "WHERE s.categoryOfBusiness = :categoryOfBusiness "
              + "AND s.minSalary >= :minSalary "
              + "AND s.maxSalary <= :maxSalary")
  Page<Member> findByCategoryOfBusiness(
      @Param("categoryOfBusiness") CategoryOfBusiness categoryOfBusiness,
      @Param("minSalary") Integer minSalary,
      @Param("maxSalary") Integer maxSalary,
      Pageable pageable);



}
