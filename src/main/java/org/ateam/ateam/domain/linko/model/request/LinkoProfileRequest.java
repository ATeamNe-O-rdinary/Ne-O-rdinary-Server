package org.ateam.ateam.domain.linko.model.request;

import jakarta.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ateam.ateam.domain.linker.model.enums.RateUnit;
import org.ateam.ateam.domain.linko.model.Linko;
import org.ateam.ateam.domain.member.entity.member.Member;
import org.ateam.ateam.domain.member.enums.*;

@Getter
@NoArgsConstructor
public class LinkoProfileRequest {

  @NotBlank(message = "회사명은 필수입니다.")
  private String companyName;

  @NotNull(message = "회사업종을 선택해주세요.")
  private CompanyType companyType;

  @NotNull(message = "대분류 카테고리를 선택해주세요.")
  private MainCategory mainCategory;

  @NotNull(message = "세부 카테고리를 선택해주세요.")
  private CategoryOfBusiness categoryOfBusiness;

  @NotBlank(message = "프로젝트 소개를 입력해주세요.")
  private String projectIntro;

  @NotNull(message = "예상 기간을 선택해주세요.")
  private ExpectedDuration expectedDuration;

  @NotNull(message = "예산 단위를 선택해주세요.")
  private RateUnit rateUnit;

  @NotNull(message = "예산 금액을 입력해주세요.")
  @Positive(message = "예산 금액은 양수여야 합니다.")
  private Integer rateAmount;

  @NotNull(message = "협업 방식을 선택해주세요.")
  private CollaborationType collaborationType;

  private Region region;

  @NotBlank(message = "마감기한을 입력해주세요.")
  private String deadline;

  @NotEmpty(message = "요구 스킬을 선택해주세요.")
  private Set<TechStack> techStacks;

  @AssertTrue(message = "선택한 세부 카테고리가 대분류와 일치하지 않습니다.")
  private boolean isCategoryValid() {
    if (mainCategory == null || categoryOfBusiness == null) {
      return true;
    }
    return categoryOfBusiness.getMainCategory() == mainCategory;
  }

  @AssertTrue(message = "오프라인 협업인 경우 지역을 선택해주세요.")
  private boolean isRegionValid() {
    if (collaborationType == CollaborationType.OFFLINE
        || collaborationType == CollaborationType.BOTH) {
      return region != null;
    }
    return true;
  }

  public Linko toEntity(Member member) {
    return Linko.builder()
        .member(member)
        .companyName(companyName)
        .companyType(companyType)
        .mainCategory(mainCategory)
        .categoryOfBusiness(categoryOfBusiness)
        .projectIntro(projectIntro)
        .expectedDuration(expectedDuration)
        .rateUnit(rateUnit)
        .rateAmount(rateAmount)
        .collaborationType(collaborationType)
        .region(region)
        .deadline(deadline)
        .techStacks(techStacks != null ? new HashSet<>(techStacks) : new HashSet<>())
        .build();
  }
}
