package org.ateam.ateam.domain.member.repository;

import org.ateam.ateam.domain.member.entity.Member;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "SELECT m FROM Member m " +
            "JOIN FETCH m.spec s " +
            "WHERE s.categoryOfBusiness = :categoryOfBusiness " +
            "AND s.minSalary >= :minSalary " +
            "AND s.maxSalary <= :maxSalary",
            countQuery = "SELECT count(m) FROM Member m JOIN m.spec s " +
                    "WHERE s.categoryOfBusiness = :categoryOfBusiness " +
                    "AND s.minSalary >= :minSalary " +
                    "AND s.maxSalary <= :maxSalary")
    Page<Member> findByCategoryOfBusiness(@Param("categoryOfBusiness") CategoryOfBusiness categoryOfBusiness,
                                          @Param("minSalary") Integer minSalary,
                                          @Param("maxSalary") Integer maxSalary,
                                          Pageable pageable);
}
