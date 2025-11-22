package org.ateam.ateam.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Spec {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CategoryOfBusiness categoryOfBusiness;

  @Column(nullable = false)
  private Integer minSalary;

  @Column(nullable = false)
  private Integer maxSalary;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;
}
