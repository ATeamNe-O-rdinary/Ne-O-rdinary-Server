package org.ateam.ateam.domain.linko.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import java.util.List;
import java.util.Set;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.ateam.ateam.domain.linker.model.enums.RateUnit;
import org.ateam.ateam.domain.linko.model.request.LinkoProfileReqDTO;
import org.ateam.ateam.domain.member.entity.Member;
import org.ateam.ateam.domain.member.enums.*;
import org.ateam.ateam.global.common.BaseEntity;
import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

@Entity
@Table(name = "linko")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Linko extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
  private Member member;

  @Column(name = "company_name", length = 100, nullable = false)
  private String companyName;

  @Enumerated(EnumType.STRING)
  @Column(name = "company_type", nullable = false)
  private CompanyType companyType;

  @Enumerated(EnumType.STRING)
  @Column(name = "main_category", nullable = false)
  private MainCategory mainCategory;

  @Enumerated(EnumType.STRING)
  @Column(name = "category_of_business", nullable = false)
  private CategoryOfBusiness categoryOfBusiness;

  @Column(name = "project_intro", columnDefinition = "TEXT", nullable = false)
  private String projectIntro;

  @Column(name = "expected_duration", nullable = false)
  private String expectedDuration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RateUnit rateUnit;

    @Column(nullable = false)
    private Integer rateAmount;

  @Enumerated(EnumType.STRING)
  @Column(name = "collaboration_type", nullable = false)
  private CollaborationType collaborationType;

  @Enumerated(EnumType.STRING)
  @Column(name = "region")
  private Region region;

  @Column(name = "deadline", nullable = false)
  private String deadline;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "linko_tech_stack", joinColumns = @JoinColumn(name = "linko_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "tech_stack")
    private Set<TechStack> techStacks;

    // 정렬 전용 필드
    private Integer calculatedMonthlyRate;

  @Builder
  public Linko(
          Member member,
          String companyName,
          CompanyType companyType,
          MainCategory mainCategory,
          CategoryOfBusiness categoryOfBusiness,
          String projectIntro,
          String expectedDuration,
          RateUnit rateUnit,
          Integer rateAmount,
          CollaborationType collaborationType,
          Region region,
          String deadline,
          Set<TechStack> techStacks) {
      this.member = member;
      this.companyName = companyName;
      this.companyType = companyType;
      this.mainCategory = mainCategory;
      this.categoryOfBusiness = categoryOfBusiness;
      this.projectIntro = projectIntro;
      this.expectedDuration = expectedDuration;
      this.rateUnit = rateUnit;
      this.rateAmount = rateAmount;
      this.collaborationType = collaborationType;
      this.region = region;
      this.deadline = deadline;
      this.techStacks = techStacks;
      this.calculateMonthlyRate();
  }
    @PrePersist
    @PreUpdate
  public void init(){
        validateCategory();
        calculateMonthlyRate();
  }


  private void validateCategory() {
    if (categoryOfBusiness.getMainCategory() != mainCategory) {
      throw new BusinessException(ErrorCode.CATEGORY_MISMATCH);
    }
  }

  // Linko.java에 update 메서드 추가
//  public void update(LinkoProfileReqDTO dto) {
//      this.companyName = dto.getCompanyName();
//      this.companyType = dto.getCompanyType();
//      this.mainCategory = dto.getMainCategory();
//      this.categoryOfBusiness = dto.getCategoryOfBusiness();
//      this.projectIntro = dto.getProjectIntro();
//      this.expectedDuration = dto.getExpectedDuration();
//      this.rateUnit = dto.getRateUnit();
//      this.rateAmount = dto.getRateAmount();
//      this.collaborationType = dto.getCollaborationType();
//      this.region = dto.getRegion();
//      this.deadline = dto.getDeadline();
//      this.techStacks = dto.getTechStacks();
//  }

  private String convertEnumListToJson(List<TechStack> list) {
    try {
      return new ObjectMapper().writeValueAsString(list);
    } catch (Exception e) {
      return "[]";
    }
  }

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
