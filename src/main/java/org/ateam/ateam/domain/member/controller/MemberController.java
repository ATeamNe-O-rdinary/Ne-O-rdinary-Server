package org.ateam.ateam.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.member.dto.req.MemberReqDTO;
import org.ateam.ateam.domain.member.dto.res.MemberResDTO;
import org.ateam.ateam.domain.member.service.MemberService;
import org.ateam.ateam.global.config.swagger.ApiErrorCodeExamples;
import org.ateam.ateam.global.dto.PagedResponse;
import org.ateam.ateam.global.dto.ResponseDto;
import org.ateam.ateam.global.error.ErrorCode;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
  private final MemberService memberService;

  // 메인화면 프로필 리스트 (슬라이드)
  @GetMapping("/api/members/profiles")
  @Operation(
      summary = "메인화면 슬라이드 프로필 목록 조회",
      description =
          """
        특정 직종와 연봉 범위 조건을 만족하는 회원의 프로필 목록을 조회합니다.

        - **categoryOfBusiness**: 조회할 직무/비즈니스 카테고리 (필수)
        - **minSalary**: 희망 최소 연봉 (필수, 해당 값 이상인 회원 검색)
        - **maxSalary**: 희망 최대 연봉 (필수, 해당 값 이하인 회원 검색)
        - **page / size**: 페이징 처리를 위한 파라미터 (기본값: page=0, size=10)
        - **sort 값은 받지 않습니다.**
        """)
  @ApiErrorCodeExamples({ErrorCode.CATEGORY_NOT_FOUND, ErrorCode.INVALID_SALARY})
  public ResponseDto<PagedResponse<MemberResDTO.ProfileListDTO>> getMemberList(
      @Valid @ModelAttribute MemberReqDTO.ProfileListDTO dto, Pageable pageable) {
    PagedResponse<MemberResDTO.ProfileListDTO> result = memberService.getProfileList(dto, pageable);
    return ResponseDto.of(HttpStatus.OK, null, "회원 프로필 목록 조회 성공", result);
  }
}
