package org.ateam.ateam.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.member.dto.req.MemberReqDTO;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;
import org.ateam.ateam.domain.member.enums.LinkTingRole;
import org.ateam.ateam.domain.member.service.MemberService;
import org.ateam.ateam.global.auth.context.UserContext;
import org.ateam.ateam.global.config.swagger.ApiErrorCodeExamples;
import org.ateam.ateam.global.dto.PagedResponse;
import org.ateam.ateam.global.dto.ResponseDto;
import org.ateam.ateam.global.error.ErrorCode;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Member", description = "회원 관련 API")
public class MemberController {
    private final UserContext userContext;
    private final MemberService memberService;

    // 메인화면 프로필 리스트 (슬라이드)

    @Operation(
            summary = "메인 페이지 슬라이드 프로필 목록 조회",
            description =
                    "업종, 급여 조건, 역할(Linker/Linko)을 기준으로 회원의 프로필 목록을 페이징하여 조회합니다. "
                            + "급여 조건 입력 시 내부적으로 월 환산 금액으로 계산되어, 해당 금액 이상인 프로필을 '월 환산 금액 내림차순'으로 정렬하여 반환합니다.")
    @ApiErrorCodeExamples({ErrorCode.INVALID_RATE_AMOUNT, ErrorCode.LINKTINGROLE_NOT_FOUND})
    @GetMapping("/api/members/profiles")
    public ResponseDto<PagedResponse<?>> getMemberList(
            @Valid @ModelAttribute MemberReqDTO.ProfileListDTO dto, Pageable pageable) {
        PagedResponse<?> result = memberService.getProfileList(dto, pageable);

        return ResponseDto.of(HttpStatus.OK, null, "회원 프로필 목록 조회 성공", result);
    }

    @Operation(
            summary = "회원 직무/업종 카테고리 조회",
            description = "현재 로그인한 회원의 역할(LINKER/LINKO)에 따라 설정된 직무 또는 업종 카테고리를 조회합니다.")
    @ApiErrorCodeExamples({ErrorCode.INVALID_RATE_AMOUNT, ErrorCode.LINKTINGROLE_NOT_FOUND})
    @GetMapping("/api/members/category")
    public ResponseDto<CategoryOfBusiness> getCategoryOfBusiness(
            @RequestParam LinkTingRole linkTingRole) {
        Long userId = userContext.getCurrentUserId();
        return ResponseDto.of(
                HttpStatus.OK,
                null,
                "회원 카테고리 조회 성공",
                memberService.getCategoryOfBusiness(userId, linkTingRole));
    }

    @Operation(
            summary = "동일 대분류 카테고리 목록 조회",
            description = "입력받은 카테고리가 속한 대분류(Main Category)의 연관된 모든 카테고리 목록을 조회합니다.")
    // 예시: Enum 변환 실패나 유효하지 않은 값 관련 에러 코드가 있다면 여기에 추가
    @ApiErrorCodeExamples({ErrorCode.INVALID_INPUT_VALUE})
    @GetMapping("/api/members/category-list")
    public ResponseDto<List<CategoryOfBusiness>> getCategoryOfBusinessList(
            @RequestParam CategoryOfBusiness categoryOfBusiness) {
        return ResponseDto.of(
                HttpStatus.OK,
                null,
                "카테고리 목록 조회 성공",
                memberService.getCategoryOfBusinessList(categoryOfBusiness));
    }

    @Operation(
            summary = "많이 찾는(인기) 회원 목록 조회",
            description =
                    """
                      특정 직무/업종 카테고리에 속한 회원들을 **누적 링크(매칭) 수** 기준으로 내림차순 정렬하여 반환합니다.
          
                      - **반환 데이터**: `linkTingRole` 값에 따라 응답 내용(Item)의 구조가 달라집니다.
                        - **LINKER**: `LinkerProfileDTO` (개인 프로필)
                        - **LINKO**: `LinkoProfileDTO` (기업 프로필)
                      """)
    @ApiErrorCodeExamples({ErrorCode.LINKTINGROLE_NOT_FOUND})
    @GetMapping("/api/members/top")
    public ResponseDto<PagedResponse<?>> getMemberTopList(
            @RequestParam List<CategoryOfBusiness> categoryOfBusinessList,
            @RequestParam LinkTingRole linkTingRole,
            Pageable pageable) {
        PagedResponse<?> result =
                memberService.getTopProfiles(categoryOfBusinessList, linkTingRole, pageable);

        return ResponseDto.of(HttpStatus.OK, null, "많이 찾는 지원자 조회 성공", result);
    }

    @Operation(
            summary = "최신 가입 회원(지원자) 목록 조회",
            description =
                    """
                      특정 직무/업종 카테고리에 속한 회원들을 **가입일(최신순)** 기준으로 정렬하여 반환합니다.
          
                      - **정렬(Sort) 주의**: 내부적으로 `createdAt` 기준으로 내림차순 정렬하므로, 요청 시 Pageable의 sort 파라미터는 무시됩니다.
                      - **반환 데이터**: `linkTingRole`에 따라 응답 구조가 다릅니다.
                        - **LINKER**: `LinkerProfileDTO`
                        - **LINKO**: `LinkoProfileDTO`
                      """)
    @ApiErrorCodeExamples({ErrorCode.LINKTINGROLE_NOT_FOUND})
    @GetMapping("/api/members/latest")
    public ResponseDto<PagedResponse<?>> getMemberLatestList(
            @RequestParam List<CategoryOfBusiness> categoryOfBusinessList,
            @RequestParam LinkTingRole linkTingRole,
            Pageable pageable) {
        PagedResponse<?> result =
                memberService.getTopProfiles(categoryOfBusinessList, linkTingRole, pageable);
        return ResponseDto.of(HttpStatus.OK, null, "최신 지원자 조회 성공", result);
    }
}