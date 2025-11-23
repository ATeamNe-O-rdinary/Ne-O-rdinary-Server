package org.ateam.ateam.domain.linker.model;

import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.ateam.ateam.domain.linker.model.entity.Linker;
import org.ateam.ateam.domain.linker.model.enums.CareerLevel;
import org.ateam.ateam.domain.linker.model.enums.RateUnit;
import org.ateam.ateam.domain.linker.model.enums.WorkTimeType;
import org.ateam.ateam.domain.member.entity.member.Member;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;
import org.ateam.ateam.domain.member.enums.CollaborationType;
import org.ateam.ateam.domain.member.enums.Region;
import org.ateam.ateam.domain.member.enums.TechStack;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LinkerFactory {

  public static Linker create(
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
    return Linker.builder()
        .member(member)
        .nickname(nickname)
        .jobCategory(jobCategory)
        .careerLevel(careerLevel)
        .oneLineDescription(oneLineDescription)
        .workTimeType(workTimeType)
        .rateUnit(rateUnit)
        .rateAmount(rateAmount)
        .collaborationType(collaborationType)
        .region(region)
        .techStacks(techStacks)
        .build();
  }
}
