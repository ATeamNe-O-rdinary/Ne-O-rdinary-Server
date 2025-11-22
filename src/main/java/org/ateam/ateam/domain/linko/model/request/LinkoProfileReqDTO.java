package org.ateam.ateam.domain.linko.model.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.ateam.ateam.domain.linko.model.Linko;
import org.ateam.ateam.domain.member.enums.*;

@Getter
@NoArgsConstructor
public class LinkoProfileReqDTO {

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

  @NotBlank(message = "예상 기간을 선택해주세요.")
  private String expectedDuration;

  @NotBlank(message = "예상 범위를 선택해주세요.")
  private String expectedScope;

  @NotNull(message = "협업 방식을 선택해주세요.")
  private CollaborationType collaborationType;

  private Region region;

  @NotBlank(message = "마감기한을 입력해주세요.")
  private String deadline;

  @NotEmpty(message = "요구 스킬을 선택해주세요.")
  private List<TechStack> requiredSkills;

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

  public Linko toEntity(Long memberId) {
    return Linko.builder()
        .memberId(memberId)
        .companyName(companyName)
        .companyType(companyType)
        .mainCategory(mainCategory)
        .categoryOfBusiness(categoryOfBusiness)
        .projectIntro(projectIntro)
        .expectedDuration(expectedDuration)
        .expectedScope(expectedScope)
        .collaborationType(collaborationType)
        .region(region)
        .deadline(deadline)
        .requiredSkills(convertEnumListToJson(requiredSkills))
        .build();
  }

  private String convertEnumListToJson(List<TechStack> list) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.writeValueAsString(list);
    } catch (Exception e) {
      return "[]";
    }
  }
}
