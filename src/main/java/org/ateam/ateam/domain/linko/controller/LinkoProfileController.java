package org.ateam.ateam.domain.linko.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ateam.ateam.domain.linko.controller.response.LinkoProfileResDTO;
import org.ateam.ateam.domain.linko.model.request.LinkoProfileReqDTO;
import org.ateam.ateam.domain.linko.service.LinkoProfileService;
import org.ateam.ateam.global.config.swagger.ApiErrorCodeExamples;
import org.ateam.ateam.global.dto.ResponseDto;
import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.security.UserAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.ateam.ateam.global.auth.context.UserContext;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/linkos")
@Tag(name = "Linko", description = "회사 프로필 관리 API")
public class LinkoProfileController {

    private final LinkoProfileService service;
    private final UserContext userContext;

    // Validation 에러를 이 Controller에서만 처리
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ResponseDto<Void>> handleValidationException(Exception ex) {
        log.warn("[Linko] Validation 에러 발생");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.of(HttpStatus.BAD_REQUEST, "C001", "입력값을 확인해주세요."));
    }

    @PostMapping
    @Operation(
            summary = "회사 프로필 등록",
            description = "회사(Linko)의 프로젝트 정보를 등록합니다.\n\n" +
                    "**등록 정보:**\n\n" +
                    "- **companyName**: 회사명\n" +
                    "- **companyType**: 업종\n" +
                    "  - MANUFACTURING, FOOD_CAFE, FOOD_HEALTH, IT_MEDIA, BEAUTY_FASHION, HOSPITAL, SEMICONDUCTOR, EDUCATION, PUBLIC_INSTITUTION, DAILY_LIFE\n" +
                    "- **mainCategory**: 대분류 카테고리\n" +
                    "  - DESIGN, MARKETING, IT_PROGRAMMING\n" +
                    "- **categoryOfBusiness**: 세부 카테고리\n" +
                    "  - Design: LOGO_BRANDING, WEB_APP_BANNER, CHARACTER_DESIGN, PACKAGE_PACKAGING, DESIGN_ETC\n" +
                    "  - Marketing: SNS_OPERATION, CONTENT_CREATION, PERFORMANCE_AD\n" +
                    "  - IT_PROGRAMMING: WEB_DEV, APP_DEV, GAME_DEV, AI_DEV, SERVER_SETUP\n" +
                    "- **projectIntro**: 프로젝트 소개\n" +
                    "- **expectedDuration**: 예상 기간\n" +
                    "  - LESS_THAN_ONE_MONTH, LESS_THAN_THREE_MONTHS, MORE_THAN_THREE_MONTHS\n" +
                    "- **rateUnit**: 예산 단위\n" +
                    "  - HOURLY, PER_CASE, MONTHLY\n" +
                    "- **rateAmount**: 예산 금액 (숫자)\n" +
                    "- **collaborationType**: 협업 방식\n" +
                    "  - ONLINE, OFFLINE, BOTH\n" +
                    "- **region**: 지역\n" +
                    "  - SEOUL, GYEONGGI, INCHEON, DAEJEON, SEJONG, CHUNGBUK, CHUNGNAM, GWANGJU, JEONBUK, JEONNAM, DAEGU, GYEONGBUK, BUSAN, ULSAN, GYEONGNAM, GANGWON, JEJU, ANYWHERE\n" +
                    "- **deadline**: 마감기한 (YYYY-MM-DD)\n" +
                    "- **techStacks**: 요구 스킬\n" +
                    "  - SWIFT, KOTLIN, JAVA, FLUTTER, REACT_NATIVE, NODE_JS, PYTHON_DJANGO_FASTAPI, SPRING_JAVA"
    )
    @ApiErrorCodeExamples({
            ErrorCode.INVALID_INPUT_VALUE,
            ErrorCode.CATEGORY_MISMATCH,
            ErrorCode.PROFILE_ALREADY_EXISTS,
            ErrorCode.AUTHENTICATION_FAILED
    })
    public ResponseEntity<ResponseDto<Void>> createLinko(
            @Valid @RequestBody LinkoProfileReqDTO request,
            @AuthenticationPrincipal UserAuthentication auth) {

        Long memberId = userContext.getCurrentUserId();
        log.debug("[Linko] 프로필 등록 요청 - memberId={}", memberId);

        service.createProfile(memberId, request);

        return ResponseEntity.ok(
                ResponseDto.of(HttpStatus.OK, "SUCCESS", "프로필이 등록되었습니다.")
        );
    }

    @GetMapping("/me")
    @Operation(
            summary = "내 프로필 조회",
            description = "등록한 회사 프로필 정보를 조회합니다."
    )
    @ApiErrorCodeExamples({
            ErrorCode.ENTITY_NOT_FOUND,
            ErrorCode.AUTHENTICATION_FAILED
    })
    public ResponseEntity<ResponseDto<LinkoProfileResDTO>> getMyLinko(
            @AuthenticationPrincipal UserAuthentication auth) {

        Long memberId = userContext.getCurrentUserId();
        log.debug("[Linko] 내 프로필 조회 - memberId={}", memberId);

        LinkoProfileResDTO profile = service.getProfile(memberId);

        return ResponseEntity.ok(
                ResponseDto.of(HttpStatus.OK, "SUCCESS", "프로필 조회 성공", profile)
        );
    }

    @GetMapping("/{linkoId}")
    @Operation(
            summary = "특정 프로필 조회",
            description = "linkoId로 특정 회사 프로필을 조회합니다."
    )
    @ApiErrorCodeExamples({
            ErrorCode.ENTITY_NOT_FOUND
    })
    public ResponseEntity<ResponseDto<LinkoProfileResDTO>> getLinkoById(
            @PathVariable Long linkoId) {

        log.debug("[Linko] 프로필 조회 - linkoId={}", linkoId);

        LinkoProfileResDTO profile = service.getProfileById(linkoId);

        return ResponseEntity.ok(
                ResponseDto.of(HttpStatus.OK, "SUCCESS", "프로필 조회 성공", profile)
        );
    }

    @PutMapping("/{linkoId}")
    @Operation(
            summary = "프로필 수정",
            description = "등록한 회사 프로필 정보를 수정합니다."
    )
    @ApiErrorCodeExamples({
            ErrorCode.INVALID_INPUT_VALUE,
            ErrorCode.CATEGORY_MISMATCH,
            ErrorCode.ENTITY_NOT_FOUND,
            ErrorCode.AUTHENTICATION_FAILED,
            ErrorCode.HANDLE_ACCESS_DENIED
    })
    public ResponseEntity<ResponseDto<Void>> updateLinko(
            @PathVariable Long linkoId,
            @Valid @RequestBody LinkoProfileReqDTO request,
            @AuthenticationPrincipal UserAuthentication auth) {

        Long memberId = userContext.getCurrentUserId();
        log.debug("[Linko] 프로필 수정 - linkoId={}, memberId={}", linkoId, memberId);

        service.updateProfile(linkoId, memberId, request);

        return ResponseEntity.ok(
                ResponseDto.of(HttpStatus.OK, "SUCCESS", "프로필이 수정되었습니다.")
        );
    }

    @DeleteMapping("/{linkoId}")
    @Operation(
            summary = "프로필 삭제",
            description = "등록한 회사 프로필을 삭제합니다."
    )
    @ApiErrorCodeExamples({
            ErrorCode.ENTITY_NOT_FOUND,
            ErrorCode.AUTHENTICATION_FAILED,
            ErrorCode.HANDLE_ACCESS_DENIED
    })
    public ResponseEntity<ResponseDto<Void>> deleteLinko(
            @PathVariable Long linkoId,
            @AuthenticationPrincipal UserAuthentication auth) {

        Long memberId = userContext.getCurrentUserId();
        log.debug("[Linko] 프로필 삭제 - linkoId={}, memberId={}", linkoId, memberId);

        service.deleteProfile(linkoId, memberId);

        return ResponseEntity.ok(
                ResponseDto.of(HttpStatus.OK, "SUCCESS", "프로필이 삭제되었습니다.")
        );
    }
}