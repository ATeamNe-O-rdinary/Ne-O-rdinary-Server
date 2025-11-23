package org.ateam.ateam.domain.linker.model.entity;

import jakarta.persistence.*;
import java.util.Set;
import lombok.*;
import org.ateam.ateam.domain.linker.model.enums.CareerLevel;
import org.ateam.ateam.domain.linker.model.enums.RateUnit;
import org.ateam.ateam.domain.linker.model.enums.WorkTimeType;
import org.ateam.ateam.domain.member.entity.member.Member;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;
import org.ateam.ateam.domain.member.enums.CollaborationType;
import org.ateam.ateam.domain.member.enums.Region;
import org.ateam.ateam.domain.member.enums.TechStack;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "linker")
public class Linker {

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

  private Integer calculatedMonthlyRate;

  private static final String DEFAULT_NICKNAME = "지훈개발자";
  private static final CategoryOfBusiness DEFAULT_JOB = CategoryOfBusiness.SNS_OPERATION;
  private static final CareerLevel DEFAULT_CAREER = CareerLevel.MID;
  private static final String DEFAULT_DESCRIPTION = "3년차 백엔드 개발자입니다.";
  private static final WorkTimeType DEFAULT_WORK = WorkTimeType.ANYTIME;
  private static final RateUnit DEFAULT_UNIT = RateUnit.MONTHLY;
  private static final int DEFAULT_RATE = 3000000;
  private static final CollaborationType DEFAULT_COLLAB = CollaborationType.ONLINE;
  private static final Region DEFAULT_REGION = Region.SEOUL;
  private static final Set<TechStack> DEFAULT_STACKS = Set.of(
      TechStack.JAVA, TechStack.SPRING_JAVA
  );

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
      Set<TechStack> techStacks
  ) {
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

    applyDefaultValues();
    calculateMonthlyRate();
  }

  private void applyDefaultValues() {
    if (nickname == null) this.nickname = DEFAULT_NICKNAME;
    if (jobCategory == null) this.jobCategory = DEFAULT_JOB;
    if (careerLevel == null) this.careerLevel = DEFAULT_CAREER;
    if (oneLineDescription == null) this.oneLineDescription = DEFAULT_DESCRIPTION;
    if (workTimeType == null) this.workTimeType = DEFAULT_WORK;
    if (rateUnit == null) this.rateUnit = DEFAULT_UNIT;
    if (rateAmount == null) this.rateAmount = DEFAULT_RATE;
    if (collaborationType == null) this.collaborationType = DEFAULT_COLLAB;
    if (region == null) this.region = DEFAULT_REGION;
    if (techStacks == null || techStacks.isEmpty()) this.techStacks = DEFAULT_STACKS;
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
      Set<TechStack> techStacks
  ) {
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

    applyDefaultValues();
    calculateMonthlyRate();
  }

  @PrePersist
  @PreUpdate
  public void calculateMonthlyRate() {
    if (this.rateAmount == null || this.rateUnit == null) {
      this.calculatedMonthlyRate = 0;
      return;
    }

    switch (this.rateUnit) {
      case WEEKLY -> this.calculatedMonthlyRate = this.rateAmount * 4;
      case HOURLY -> this.calculatedMonthlyRate = this.rateAmount * 8 * 30;
      default -> this.calculatedMonthlyRate = this.rateAmount;
    }
  }
}
