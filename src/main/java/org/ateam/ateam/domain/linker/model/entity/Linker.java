package org.ateam.ateam.domain.linker.model.entity;

import jakarta.persistence.*;
import java.util.Set;
import lombok.*;
import org.ateam.ateam.domain.linker.model.enums.CareerLevel;
import org.ateam.ateam.domain.linker.model.enums.RateUnit;
import org.ateam.ateam.domain.linker.model.enums.WorkTimeType;
import org.ateam.ateam.domain.member.entity.Member;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;
import org.ateam.ateam.domain.member.enums.CollaborationType;
import org.ateam.ateam.domain.member.enums.Region;
import org.ateam.ateam.domain.member.enums.TechStack;
import org.ateam.ateam.global.common.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "linker")
public class Linker extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "linker_id")
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false, unique = true)
  private Member member;

  @Column(nullable = false, length = 30)
  private String nickname;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 50)
  private CategoryOfBusiness jobCategory;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private CareerLevel careerLevel;

  @Column(nullable = false, length = 100)
  private String oneLineDescription;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private WorkTimeType workTimeType;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private RateUnit rateUnit;

  @Column(nullable = false)
  private Integer rateAmount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private CollaborationType collaborationType;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private Region region;

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "linker_tech_stack", joinColumns = @JoinColumn(name = "linker_id"))
  @Enumerated(EnumType.STRING)
  @Column(name = "tech_stack")
  private Set<TechStack> techStacks;

  // 정렬 전용 필드
  private Integer calculatedMonthlyRate;

  @Builder
  private Linker(
      Member member,
      String nickname,
      CategoryOfBusiness jobCategory,
      CareerLevel careerLevel,
      String oneLineDescription,
      WorkTimeType workTimeType,
      RateUnit rateUnit,
      Integer rateAmount,
      CollaborationType collaborationType,
      Region region,
      Set<TechStack> techStacks) {
    this.member = member;
    this.nickname = nickname;
    this.jobCategory = jobCategory;
    this.careerLevel = careerLevel;
    this.oneLineDescription = oneLineDescription;
    this.workTimeType = workTimeType;
    this.rateUnit = rateUnit;
    this.rateAmount = rateAmount;
    this.collaborationType = collaborationType;
    this.region = region;
    this.techStacks = techStacks;
    this.calculateMonthlyRate();
  }

  public void update(
      String nickname,
      CategoryOfBusiness jobCategory,
      CareerLevel careerLevel,
      String oneLineDescription,
      WorkTimeType workTimeType,
      RateUnit rateUnit,
      Integer rateAmount,
      CollaborationType collaborationType,
      Region region,
      Set<TechStack> techStacks) {
    this.nickname = nickname;
    this.jobCategory = jobCategory;
    this.careerLevel = careerLevel;
    this.oneLineDescription = oneLineDescription;
    this.workTimeType = workTimeType;
    this.rateUnit = rateUnit;
    this.rateAmount = rateAmount;
    this.collaborationType = collaborationType;
    this.region = region;

    this.techStacks.clear();
    this.techStacks.addAll(techStacks);
  }

  @PrePersist // DB에 insert 되기 직전에 실행
  @PreUpdate // DB에 update 되기 직전에 실행
  public void calculateMonthlyRate() {
    if (this.rateAmount == null || this.rateUnit == null) {
      this.calculatedMonthlyRate = 0;
      return;
    }

    switch (this.rateUnit) {
      case WEEKLY:
        // 주급 * 4
        this.calculatedMonthlyRate = this.rateAmount * 4;
        break;
      case HOURLY:
        // 시급 * 8 * 30
        this.calculatedMonthlyRate = this.rateAmount * 8 * 30;
        break;
      case MONTHLY:
      default:
        // 월급은 그대로 적용
        this.calculatedMonthlyRate = this.rateAmount;
        break;
    }
  }
}
