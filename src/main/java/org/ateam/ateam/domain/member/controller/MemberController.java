package org.ateam.ateam.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.member.dto.req.MemberReqDTO;
import org.ateam.ateam.domain.member.dto.res.MemberResDTO;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;
import org.ateam.ateam.domain.member.service.MemberService;
import org.ateam.ateam.global.auth.context.UserContext;
import org.ateam.ateam.global.config.swagger.ApiErrorCodeExamples;
import org.ateam.ateam.global.dto.PagedResponse;
import org.ateam.ateam.global.dto.ResponseDto;
import org.ateam.ateam.global.error.ErrorCode;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final UserContext userContext;
  private final MemberService memberService;

  // 메인화면 프로필 리스트 (슬라이드)

    @Operation(
            summary = "메인 페이지 슬라이드 프로필 목록 조회",
            description = "업종, 급여 조건, 역할(Linker/Linko)을 기준으로 회원의 프로필 목록을 페이징하여 조회합니다. " +
                    "급여 조건 입력 시 내부적으로 월 환산 금액으로 계산되어, 해당 금액 이상인 프로필을 '월 환산 금액 내림차순'으로 정렬하여 반환합니다."
    )
  @ApiErrorCodeExamples({ErrorCode.INVALID_RATE_AMOUNT, ErrorCode.LINKTINGROLE_NOT_FOUND})
  @GetMapping("/api/members/profiles")
  public ResponseDto<PagedResponse<?>> getMemberList(@Valid @ModelAttribute MemberReqDTO.ProfileListDTO dto, Pageable pageable) {
    PagedResponse<?> result = memberService.getProfileList(dto, pageable);



    return ResponseDto.of(HttpStatus.OK, null, "회원 프로필 목록 조회 성공", result);
  }

  @GetMapping("/api/members/category")
  public ResponseDto<CategoryOfBusiness> getCategoryOfBusiness(@RequestParam String linkTingRole){
      Long userId = userContext.getCurrentUserId();
      return ResponseDto.of(HttpStatus.OK, null, "회원 카테고리 조회 성공", memberService.getCategoryOfBusiness(userId, linkTingRole));

  }

//  @GetMapping("/api/members/category-list")
//    public ResponseDto<List<CategoryOfBusiness>> getCategoryOfBusinessList(@RequestParam CategoryOfBusiness categoryOfBusiness){
//      return ResponseDto.of(HttpStatus.OK, null, "카테고리 목록 조회 성공", memberService.getCategoryOfBusinessList(categoryOfBusiness));
//
//  }

//  @GetMapping("/api/members/top")
//    public ResponseDto<PagedResponse<MemberResDTO.ProfileListDTO>> getMemberTopList(@RequestBody MemberReqDTO.TopListDTO dto){
//
//
//  }
}
