package org.ateam.ateam.domain.linko.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.ateam.ateam.domain.linko.service.LinkoProfileService;
import org.ateam.ateam.domain.linko.model.request.LinkoProfileReqDTO;
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
                  "- **companyName**: 회사명\n" +
                  "- **companyType**: 업종\n" +
                  "  - MANUFACTURING, CONSTRUCTION, WHOLESALE_RETAIL, ACCOMMODATION_FOOD, TRANSPORT_WAREHOUSE, INFORMATION_COMMUNICATION, FINANCE_INSURANCE, REAL_ESTATE, PROFESSIONAL_TECHNICAL\n" +
                  "- **mainCategory**: 대분류 카테고리\n" +
                  "  - DESIGN, MARKETING, IT_PROGRAMMING\n" +
                  "- **categoryOfBusiness**: 세부 카테고리\n" +
                  "  - Design: LOGO_BRANDING, WEB_APP_BANNER, CHARACTER_DESIGN, PACKAGE_PACKAGING, DESIGN_ETC\n" +
                  "  - Marketing: SNS_OPERATION, CONTENT_CREATION, PERFORMANCE_AD\n" +
                  "  - IT_PROGRAMMING: WEB_DEV, APP_DEV, GAME_DEV, AI_DEV, SERVER_SETUP\n" +
                  "- **projectIntro**: 프로젝트 소개\n" +
                  "- **expectedDuration**: 예상 기간\n" +
                  "- **expectedScope**: 예상 범위\n" +
                  "- **collaborationType**: 협업 방식\n" +
                  "  - ONLINE, OFFLINE, BOTH\n" +
                  "- **region**: 지역\n" +
                  "  - SEOUL, GYEONGGI, INCHEON, DAEJEON, SEJONG, CHUNGBUK, CHUNGNAM, GWANGJU, JEONBUK, JEONNAM, DAEGU, GYEONGBUK, BUSAN, ULSAN, GYEONGNAM, GANGWON, JEJU, ANYWHERE\n" +
                  "- **deadline**: 마감기한\n" +
                  "  - YYYY-MM-DD\n" +
                  "- **requiredSkills**: 요구 스킬\n" +
                  "  - SWIFT, KOTLIN, JAVA, FLUTTER, REACT, NODEJS, DJANGO, FASTAPI"
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
