package org.ateam.ateam.domain.member.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.linker.model.entity.Linker;
import org.ateam.ateam.domain.linker.model.enums.RateUnit;
import org.ateam.ateam.domain.linker.repository.LinkerRepository;
import org.ateam.ateam.domain.linko.model.Linko;
import org.ateam.ateam.domain.linko.repository.LinkoRepository;
import org.ateam.ateam.domain.member.converter.MemberConverter;
import org.ateam.ateam.domain.member.dto.req.MemberReqDTO;
import org.ateam.ateam.domain.member.dto.res.MemberResDTO;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;
import org.ateam.ateam.domain.member.enums.LinkTingRole;
import org.ateam.ateam.domain.member.exception.MemberException;
import org.ateam.ateam.domain.member.repository.MemberRepository;
import org.ateam.ateam.domain.member.service.MemberService;
import org.ateam.ateam.global.dto.PagedResponse;
import org.ateam.ateam.global.error.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
  private final MemberRepository memberRepository;
  private final LinkerRepository linkerRepository;
  private final LinkoRepository linkoRepository;

  @Override
  @Transactional
  public PagedResponse<?> getProfileList(MemberReqDTO.ProfileListDTO dto, Pageable pageable) {

    CategoryOfBusiness category = dto.categoryOfBusiness();
    RateUnit rateUnit = dto.rateUnit();
    Integer rateAmount = dto.rateAmount();
    LinkTingRole linkTingRole = dto.linkTingRole();

    Integer calculatedMonthlyRate = 0;
    switch (rateUnit) {
      case WEEKLY:
        // 주급 * 4
        calculatedMonthlyRate = rateAmount * 4;
        break;
      case HOURLY:
        // 시급 * 8 * 30
        calculatedMonthlyRate = rateAmount * 8 * 30;
        break;
      case MONTHLY:
      default:
        // 월급은 그대로 적용
        calculatedMonthlyRate = rateAmount;
        break;
    }
    Pageable unsortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

    if (linkTingRole == LinkTingRole.LINKER) {
      Page<Linker> linker =
          linkerRepository.findByCategoryAndRateGreaterThan(
              category, calculatedMonthlyRate, unsortedPageable);
      Page<MemberResDTO.LinkerProfileDTO> pageList =
          linker.map(MemberConverter::toLinkerProfileDTO);
      return PagedResponse.pagedFrom(pageList);
    } else if (linkTingRole == LinkTingRole.LINKO) {
      Page<Linko> linko =
          linkoRepository.findByCategoryAndRateGreaterThan(
              category, calculatedMonthlyRate, unsortedPageable);
      Page<MemberResDTO.LinkoProfileDTO> pageList = linko.map(MemberConverter::toLinkoProfileDTO);
      return PagedResponse.pagedFrom(pageList);

    } else {
      throw new MemberException(ErrorCode.LINKTINGROLE_NOT_FOUND);
    }
  }

  @Override
  @Transactional
  public CategoryOfBusiness getCategoryOfBusiness(Long memberId, LinkTingRole linkTingRole) {

    if (linkTingRole == LinkTingRole.LINKER) {
      Linker linker =
          linkerRepository
              .findByMemberId(memberId)
              .orElseThrow(() -> new MemberException(ErrorCode.USER_NOT_FOUND));
      return linker.getJobCategory();

    } else if (linkTingRole == LinkTingRole.LINKO) {
      Linko linko =
          linkoRepository
              .findByMemberId(memberId)
              .orElseThrow(() -> new MemberException(ErrorCode.USER_NOT_FOUND));
      return linko.getCategoryOfBusiness();

    } else {
      throw new MemberException(ErrorCode.LINKTINGROLE_NOT_FOUND);
    }
  }

  @Override
  public List<CategoryOfBusiness> getCategoryOfBusinessList(CategoryOfBusiness categoryOfBusiness) {
    return categoryOfBusiness.getSameMainCategoryList();
  }

  @Override
  public PagedResponse<?> getTopProfiles(
      List<CategoryOfBusiness> categoryOfBusinessList,
      LinkTingRole linkTingRole,
      Pageable pageable) {
    Pageable unsortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
    if (linkTingRole == LinkTingRole.LINKER) {
      Page<Linker> linker =
          linkerRepository.findByCategoryOfBusinessInOrderByLinkCountDesc(
              categoryOfBusinessList, unsortedPageable);
      Page<MemberResDTO.LinkerProfileDTO> pageList =
          linker.map(MemberConverter::toLinkerProfileDTO);
      return PagedResponse.pagedFrom(pageList);
    } else if (linkTingRole == LinkTingRole.LINKO) {
      Page<Linko> linko =
          linkoRepository.findByCategoryOfBusinessInOrderByLinkCountDesc(
              categoryOfBusinessList, unsortedPageable);
      Page<MemberResDTO.LinkoProfileDTO> pageList = linko.map(MemberConverter::toLinkoProfileDTO);
      return PagedResponse.pagedFrom(pageList);
    } else {
      throw new MemberException(ErrorCode.LINKTINGROLE_NOT_FOUND);
    }
  }

  @Override
  public PagedResponse<?> getLatestProfiles(
      List<CategoryOfBusiness> categoryOfBusinessList,
      LinkTingRole linkTingRole,
      Pageable pageable) {
    Pageable unsortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
    if (linkTingRole == LinkTingRole.LINKER) {
      Page<Linker> linker =
          linkerRepository.findByCategoryOfBusinessInOrderByIdDesc(
              categoryOfBusinessList, unsortedPageable);
      Page<MemberResDTO.LinkerProfileDTO> pageList =
          linker.map(MemberConverter::toLinkerProfileDTO);
      return PagedResponse.pagedFrom(pageList);
    } else if (linkTingRole == LinkTingRole.LINKO) {
      Page<Linko> linko =
          linkoRepository.findByCategoryOfBusinessInOrderByIdDesc(
              categoryOfBusinessList, unsortedPageable);
      Page<MemberResDTO.LinkoProfileDTO> pageList = linko.map(MemberConverter::toLinkoProfileDTO);
      return PagedResponse.pagedFrom(pageList);
    } else {
      throw new MemberException(ErrorCode.LINKTINGROLE_NOT_FOUND);
    }
  }
}
