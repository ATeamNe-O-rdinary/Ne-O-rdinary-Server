package org.ateam.ateam.domain.linko.controller.response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

import org.ateam.ateam.domain.linko.model.Linko;
import org.ateam.ateam.domain.member.enums.*;

@Getter
@Builder
public class LinkoProfileResDTO {
  private Long id;
  private String companyName;
  private CompanyType companyType;
  private MainCategory mainCategory;
  private CategoryOfBusiness categoryOfBusiness;
  private String projectIntro;
  private String expectedDuration;
  private String expectedScope;
  private CollaborationType collaborationType;
  private Region region;
  private String deadline;
  private List<TechStack> requiredSkills;

  public static LinkoProfileResDTO from(Linko linko) {
    return LinkoProfileResDTO.builder()
        .id(linko.getId())
        .companyName(linko.getCompanyName())
        .companyType(linko.getCompanyType())
        .mainCategory(linko.getMainCategory())
        .categoryOfBusiness(linko.getCategoryOfBusiness())
        .projectIntro(linko.getProjectIntro())
        .expectedDuration(linko.getExpectedDuration())
//        .expectedScope(linko.getExpectedScope())
        .collaborationType(linko.getCollaborationType())
        .region(linko.getRegion())
        .deadline(linko.getDeadline())
//        .requiredSkills(parseSkills(linko.getRequiredSkills()))
        .build();
  }

  private static List<TechStack> parseSkills(String json) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(json, new TypeReference<>() {});
    } catch (Exception e) {
      return List.of();
    }
  }
}
