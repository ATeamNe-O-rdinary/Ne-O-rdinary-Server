package org.ateam.ateam.domain.member.dto.res;

import java.util.Set;
import lombok.Builder;
import org.ateam.ateam.domain.linker.model.enums.*;
import org.ateam.ateam.domain.member.enums.*;

public class MemberResDTO {

  @Builder
  public record ProfileListDTO(Long memberId, String username, String profileImage) {}

  @Builder
  public record LinkoProfileDTO(
      String companyName,
      CompanyType companyType,
      MainCategory mainCategory,
      CategoryOfBusiness categoryOfBusiness,
      String projectIntro,
      ExpectedDuration expectedDuration,
      RateUnit rateUnit,
      Integer rateAmount,
      CollaborationType collaborationType,
      Region region,
      String deadline,
      Set<TechStack> techStacks,
      String profileImage) {}

  @Builder
  public record LinkerProfileDTO(
      String nickname,
      CategoryOfBusiness jobCategory,
      CareerLevel careerLevel,
      String oneLineDescription,
      WorkTimeType workTimeType,
      RateUnit rateUnit,
      Integer rateAmount,
      CollaborationType collaborationType,
      Region region,
      Set<TechStack> techStacks,
      String profileImage) {}

  @Builder
  public record TopListDTO() {}
}
