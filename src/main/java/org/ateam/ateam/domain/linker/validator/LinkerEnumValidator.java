package org.ateam.ateam.domain.linker.validator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.linker.exception.InvalidLinkerEnumException;
import org.ateam.ateam.domain.linker.model.enums.CareerLevel;
import org.ateam.ateam.domain.linker.model.enums.RateUnit;
import org.ateam.ateam.domain.linker.model.enums.WorkTimeType;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;
import org.ateam.ateam.domain.member.enums.CollaborationType;
import org.ateam.ateam.domain.member.enums.Region;
import org.ateam.ateam.domain.member.enums.TechStack;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkerEnumValidator {

  public CategoryOfBusiness parseJobCategory(String title) {
    return CategoryOfBusiness.from(title); // 내부에서 오류 처리됨
  }

  public CareerLevel parseCareerLevel(String level) {
    try {
      return CareerLevel.valueOf(level);
    } catch (Exception e) {
      throw new InvalidLinkerEnumException();
    }
  }

  public WorkTimeType parseWorkTimeType(String type) {
    try {
      return WorkTimeType.valueOf(type);
    } catch (Exception e) {
      throw new InvalidLinkerEnumException();
    }
  }

  public RateUnit parseRateUnit(String unit) {
    try {
      return RateUnit.valueOf(unit);
    } catch (Exception e) {
      throw new InvalidLinkerEnumException();
    }
  }

  public CollaborationType parseCollaborationType(String type) {
    try {
      return CollaborationType.valueOf(type);
    } catch (Exception e) {
      throw new InvalidLinkerEnumException();
    }
  }

  public Region parseRegion(String region) {
    try {
      return Region.valueOf(region);
    } catch (Exception e) {
      throw new InvalidLinkerEnumException();
    }
  }

  public Set<TechStack> parseTechStacks(List<String> list) {
    if (list == null || list.isEmpty()) return Set.of();
    try {
      return list.stream().map(TechStack::valueOf).collect(Collectors.toSet());
    } catch (Exception e) {
      throw new InvalidLinkerEnumException();
    }
  }
}
