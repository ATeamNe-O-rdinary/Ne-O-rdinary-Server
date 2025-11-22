package org.ateam.ateam.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ateam.ateam.domain.member.dto.req.LinkoProfileReqDTO;
import org.ateam.ateam.domain.member.service.LinkoProfileService;
import org.ateam.ateam.global.auth.context.UserContext;
import org.ateam.ateam.global.config.swagger.ApiErrorCodeExamples;
import org.ateam.ateam.global.dto.ResponseDto;
import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.security.UserAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/linko/profile")
@Tag(name = "Linko Profile", description = "Linko 프로필 관리 API")
public class LinkoProfileController {

  private final LinkoProfileService service;
  private final UserContext userContext;

  @PostMapping
  @Operation(
          summary = "회사 프로필 등록 (Create Company Profile)",
          description = "회사(Linko)의 프로젝트 정보를 등록합니다.\n\n" +
                  "**등록 정보:**\n\n" +
                  "- **companyName**: 회사명 (Company name)\n" +
                  "- **companyType**: 업종 (Business type)\n" +
                  "  - MANUFACTURING, CONSTRUCTION, WHOLESALE_RETAIL, ACCOMMODATION_FOOD, TRANSPORT_WAREHOUSE, INFORMATION_COMMUNICATION, FINANCE_INSURANCE, REAL_ESTATE, PROFESSIONAL_TECHNICAL\n" +
                  "- **mainCategory**: 대분류 카테고리 (Main category)\n" +
                  "  - DESIGN, DEVELOPMENT, MARKETING, CONTENT, BUSINESS\n" +
                  "- **categoryOfBusiness**: 세부 카테고리 (Sub category)\n" +
                  "  - Design: LOGO_BRANDING, WEB_MOBILE_DESIGN, PRODUCT_PACKAGE, ILLUSTRATION, PRINT_EDITORIAL\n" +
                  "  - Development: WEB_DEVELOPMENT, APP_DEVELOPMENT, GAME_DEVELOPMENT, AI_DATA, SYSTEM_NETWORK\n" +
                  "  - Marketing: DIGITAL_MARKETING, CONTENT_MARKETING, SNS_MARKETING, BRAND_STRATEGY, SEO_SEM\n" +
                  "  - Content: VIDEO_PRODUCTION, PHOTOGRAPHY, WRITING, TRANSLATION, VOICE_NARRATION\n" +
                  "  - Business: BUSINESS_CONSULTING, ACCOUNTING_TAX, LEGAL_PATENT, HR_EDUCATION, RESEARCH_ANALYSIS\n" +
                  "- **projectIntro**: 프로젝트 소개 (Project introduction)\n" +
                  "- **expectedDuration**: 예상 기간 (Expected duration)\n" +
                  "- **expectedScope**: 예상 범위 (Expected scope)\n" +
                  "- **collaborationType**: 협업 방식 (Collaboration type)\n" +
                  "  - ONLINE (온라인), OFFLINE (오프라인), HYBRID (혼합)\n" +
                  "- **region**: 지역 (Region)\n" +
                  "  - SEOUL, GYEONGGI, INCHEON, BUSAN, DAEGU, GWANGJU, DAEJEON, ULSAN, SEJONG, GANGWON, CHUNGBUK, CHUNGNAM, JEONBUK, JEONNAM, GYEONGBUK, GYEONGNAM, JEJU, OVERSEAS\n" +
                  "- **deadline**: 마감기한 (Deadline)\n" +
                  "- **requiredSkills**: 요구 스킬 (Required skills)\n" +
                  "  - JAVA, PYTHON, JAVASCRIPT, TYPESCRIPT, REACT, VUE, ANGULAR, SPRING, DJANGO, NODEJS, SWIFT, KOTLIN, FLUTTER, FIGMA, PHOTOSHOP, ILLUSTRATOR 등"
  )
  @ApiErrorCodeExamples({
    ErrorCode.INVALID_INPUT_VALUE,
    ErrorCode.CATEGORY_MISMATCH,
    ErrorCode.PROFILE_ALREADY_EXISTS,
    ErrorCode.AUTHENTICATION_FAILED
  })
  public ResponseEntity<ResponseDto<Void>> createProfile(
      @Valid @RequestBody LinkoProfileReqDTO request,
      @AuthenticationPrincipal UserAuthentication auth) {

    Long memberId = userContext.getCurrentUserId();
    log.debug("[Linko Profile] 프로필 등록 요청 - memberId={}", memberId);

    service.createProfile(memberId, request);

    return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, null, "프로필이 등록되었습니다."));
  }

  //    @GetMapping
  //    @Operation(
  //            summary = "내 프로필 조회",
  //            description = "등록한 회사 프로필 정보를 조회합니다."
  //    )
  //    @ApiErrorCodeExamples({
  //            ErrorCode.ENTITY_NOT_FOUND,
  //            ErrorCode.AUTHENTICATION_FAILED
  //    })
  //    public ResponseEntity<ResponseDto<LinkoProfileResDTO>> getMyProfile(
  //            @AuthenticationPrincipal UserAuthentication auth) {
  //
  //        Long memberId = userContext.getCurrentUserId();
  //        log.debug("[Linko Profile] 프로필 조회 - memberId={}", memberId);
  //
  //        LinkoProfileResDTO profile = service.getProfile(memberId);
  //
  //        return ResponseEntity.ok(
  //                ResponseDto.of(HttpStatus.OK, null, "프로필 조회 성공", profile)
  //        );
  //    }

  @PutMapping
  @Operation(summary = "회사 프로필 수정", description = "등록한 회사 프로필 정보를 수정합니다.")
  @ApiErrorCodeExamples({
    ErrorCode.INVALID_INPUT_VALUE,
    ErrorCode.CATEGORY_MISMATCH,
    ErrorCode.ENTITY_NOT_FOUND,
    ErrorCode.AUTHENTICATION_FAILED
  })
  public ResponseEntity<ResponseDto<Void>> updateProfile(
      @Valid @RequestBody LinkoProfileReqDTO request,
      @AuthenticationPrincipal UserAuthentication auth) {

    Long memberId = userContext.getCurrentUserId();
    log.debug("[Linko Profile] 프로필 수정 - memberId={}", memberId);

    service.updateProfile(memberId, request);

    return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, null, "프로필이 수정되었습니다."));
  }

  @DeleteMapping
  @Operation(summary = "회사 프로필 삭제", description = "등록한 회사 프로필을 삭제합니다.")
  @ApiErrorCodeExamples({ErrorCode.ENTITY_NOT_FOUND, ErrorCode.AUTHENTICATION_FAILED})
  public ResponseEntity<ResponseDto<Void>> deleteProfile(
      @AuthenticationPrincipal UserAuthentication auth) {

    Long memberId = userContext.getCurrentUserId();
    log.debug("[Linko Profile] 프로필 삭제 - memberId={}", memberId);

    service.deleteProfile(memberId);

    return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, null, "프로필이 삭제되었습니다."));
  }
}
