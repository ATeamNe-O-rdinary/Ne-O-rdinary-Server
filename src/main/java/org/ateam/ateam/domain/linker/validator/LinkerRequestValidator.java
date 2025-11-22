package org.ateam.ateam.domain.linker.validator;

import org.ateam.ateam.domain.linker.exception.InvalidLinkerFieldException;
import org.ateam.ateam.domain.linker.model.request.LinkerCreateRequest;
import org.ateam.ateam.domain.linker.model.request.LinkerUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class LinkerRequestValidator {

  public void validateCreate(LinkerCreateRequest req) {
    validateCommon(
        req.nickname(),
        req.jobCategory(),
        req.careerLevel(),
        req.oneLineDescription(),
        req.workTimeType(),
        req.rateUnit(),
        req.rateAmount(),
        req.collaborationType(),
        req.region());
  }

  public void validateUpdate(LinkerUpdateRequest req) {
    validateCommon(
        req.nickname(),
        req.jobCategory(),
        req.careerLevel(),
        req.oneLineDescription(),
        req.workTimeType(),
        req.rateUnit(),
        req.rateAmount(),
        req.collaborationType(),
        req.region());
  }

  private void validateCommon(
      String nickname,
      String jobCategory,
      String careerLevel,
      String oneLineDescription,
      String workTimeType,
      String rateUnit,
      Integer rateAmount,
      String collaborationType,
      String region) {
    if (nickname == null || nickname.isBlank()) throw new InvalidLinkerFieldException();
    if (jobCategory == null || jobCategory.isBlank()) throw new InvalidLinkerFieldException();
    if (careerLevel == null || careerLevel.isBlank()) throw new InvalidLinkerFieldException();
    if (oneLineDescription == null || oneLineDescription.isBlank())
      throw new InvalidLinkerFieldException();
    if (workTimeType == null || workTimeType.isBlank()) throw new InvalidLinkerFieldException();
    if (rateUnit == null || rateUnit.isBlank()) throw new InvalidLinkerFieldException();
    if (rateAmount == null || rateAmount <= 0) throw new InvalidLinkerFieldException();
    if (collaborationType == null || collaborationType.isBlank())
      throw new InvalidLinkerFieldException();
    if (region == null || region.isBlank()) throw new InvalidLinkerFieldException();
  }
}
