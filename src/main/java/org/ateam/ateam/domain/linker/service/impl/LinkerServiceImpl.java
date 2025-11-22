package org.ateam.ateam.domain.linker.service.impl;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.linker.controller.response.LinkerResponse;
import org.ateam.ateam.domain.linker.exception.LinkerNotFoundException;
import org.ateam.ateam.domain.linker.model.LinkerFactory;
import org.ateam.ateam.domain.linker.model.entity.Linker;
import org.ateam.ateam.domain.linker.model.enums.CareerLevel;
import org.ateam.ateam.domain.linker.model.enums.RateUnit;
import org.ateam.ateam.domain.linker.model.enums.WorkTimeType;
import org.ateam.ateam.domain.linker.model.mapper.LinkerMapper;
import org.ateam.ateam.domain.linker.model.request.LinkerCreateRequest;
import org.ateam.ateam.domain.linker.model.request.LinkerUpdateRequest;
import org.ateam.ateam.domain.linker.repository.LinkerRepository;
import org.ateam.ateam.domain.linker.service.LinkerService;
import org.ateam.ateam.domain.linker.validator.LinkerEnumValidator;
import org.ateam.ateam.domain.linker.validator.LinkerExistValidator;
import org.ateam.ateam.domain.linker.validator.LinkerRequestValidator;
import org.ateam.ateam.domain.member.entity.Member;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;
import org.ateam.ateam.domain.member.enums.CollaborationType;
import org.ateam.ateam.domain.member.enums.Region;
import org.ateam.ateam.domain.member.enums.TechStack;
import org.ateam.ateam.global.dto.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LinkerServiceImpl implements LinkerService {

  private final LinkerRepository linkerRepository;

  private final LinkerRequestValidator requestValidator;
  private final LinkerExistValidator existValidator;
  private final LinkerEnumValidator enumValidator;

  private final LinkerMapper linkerMapper;

  @Override
  @Transactional
  public LinkerResponse linkerCreate(Long memberId, LinkerCreateRequest request) {

    requestValidator.validateCreate(request);
    existValidator.validateLinkerNotExists(memberId);

    Member member = existValidator.getMemberOrThrow(memberId);

    ParsedEnums parsed =
        parseEnums(
            request.jobCategory(),
            request.careerLevel(),
            request.workTimeType(),
            request.rateUnit(),
            request.collaborationType(),
            request.region(),
            request.techStacks());

    Linker linker =
        LinkerFactory.create(
            member,
            request.nickname(),
            parsed.jobCategory(),
            parsed.careerLevel(),
            request.oneLineDescription(),
            parsed.workTimeType(),
            parsed.rateUnit(),
            request.rateAmount(),
            parsed.collaborationType(),
            parsed.region(),
            parsed.techStacks());

    return linkerMapper.toResponse(linkerRepository.save(linker));
  }

  @Override
  public LinkerResponse getById(Long linkerId) {
    Linker linker = linkerRepository.findById(linkerId).orElseThrow(LinkerNotFoundException::new);
    return linkerMapper.toResponse(linker);
  }

  @Override
  public PagedResponse<LinkerResponse> getPage(Pageable pageable) {
    Page<Linker> page = linkerRepository.findAll(pageable);
    return PagedResponse.of(page, linkerMapper::toResponse);
  }

  @Override
  @Transactional
  public LinkerResponse linkerUpdate(Long memberId, Long linkerId, LinkerUpdateRequest request) {

    requestValidator.validateUpdate(request);

    Linker linker = existValidator.getOwnedLinkerOrThrow(linkerId, memberId);

    ParsedEnums parsed =
        parseEnums(
            request.jobCategory(),
            request.careerLevel(),
            request.workTimeType(),
            request.rateUnit(),
            request.collaborationType(),
            request.region(),
            request.techStacks());

    linker.update(
        request.nickname(),
        parsed.jobCategory(),
        parsed.careerLevel(),
        request.oneLineDescription(),
        parsed.workTimeType(),
        parsed.rateUnit(),
        request.rateAmount(),
        parsed.collaborationType(),
        parsed.region(),
        parsed.techStacks());

    return linkerMapper.toResponse(linker);
  }

  @Override
  @Transactional
  public void linkerDelete(Long memberId, Long linkerId) {
    Linker linker = existValidator.getOwnedLinkerOrThrow(linkerId, memberId);
    linkerRepository.delete(linker);
  }

  private record ParsedEnums(
      CategoryOfBusiness jobCategory,
      CareerLevel careerLevel,
      WorkTimeType workTimeType,
      RateUnit rateUnit,
      CollaborationType collaborationType,
      Region region,
      Set<TechStack> techStacks) {}

  private ParsedEnums parseEnums(
      String jobCategory,
      String careerLevel,
      String workTimeType,
      String rateUnit,
      String collaborationType,
      String region,
      List<String> techStacks) {
    return new ParsedEnums(
        enumValidator.parseJobCategory(jobCategory),
        enumValidator.parseCareerLevel(careerLevel),
        enumValidator.parseWorkTimeType(workTimeType),
        enumValidator.parseRateUnit(rateUnit),
        enumValidator.parseCollaborationType(collaborationType),
        enumValidator.parseRegion(region),
        enumValidator.parseTechStacks(techStacks));
  }
}
