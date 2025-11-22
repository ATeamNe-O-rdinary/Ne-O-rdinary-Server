package org.ateam.ateam.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ateam.ateam.domain.member.dto.req.LinkoProfileReqDTO;
import org.ateam.ateam.domain.member.dto.res.LinkoProfileResDTO;
import org.ateam.ateam.domain.member.service.LinkoProfileService;
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
@Tag(name = "Linko Profile", description = "회사 프로필 관리 API")
public class LinkoProfileController {

    private final LinkoProfileService service;

    @PostMapping
    @Operation(
            summary = "회사 프로필 등록",
            description = "회사의 프로젝트 정보를 등록합니다.\n\n" +
                    "**등록 정보:**\n" +
                    "- 회사명, 업종\n" +
                    "- 카테고리 (대분류/세부)\n" +
                    "- 프로젝트 소개\n" +
                    "- 예상 기간/범위\n" +
                    "- 협업 방식 및 지역\n" +
                    "- 마감기한, 요구 스킬"
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

        Long memberId = auth.getUserId();
        log.debug("[Linko Profile] 프로필 등록 요청 - memberId={}", memberId);

        service.createProfile(memberId, request);

        return ResponseEntity.ok(
                ResponseDto.of(HttpStatus.OK, "SUCCESS", "프로필이 등록되었습니다.")
        );
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
//        Long memberId = auth.getUserId();
//        log.debug("[Linko Profile] 프로필 조회 - memberId={}", memberId);
//
//        LinkoProfileResDTO profile = service.getProfile(memberId);
//
//        return ResponseEntity.ok(
//                ResponseDto.of(HttpStatus.OK, "SUCCESS", "프로필 조회 성공", profile)
//        );
//    }

    @PutMapping
    @Operation(
            summary = "프로필 수정",
            description = "등록한 회사 프로필 정보를 수정합니다."
    )
    @ApiErrorCodeExamples({
            ErrorCode.INVALID_INPUT_VALUE,
            ErrorCode.CATEGORY_MISMATCH,
            ErrorCode.ENTITY_NOT_FOUND,
            ErrorCode.AUTHENTICATION_FAILED
    })
    public ResponseEntity<ResponseDto<Void>> updateProfile(
            @Valid @RequestBody LinkoProfileReqDTO request,
            @AuthenticationPrincipal UserAuthentication auth) {

        Long memberId = auth.getUserId();
        log.debug("[Linko Profile] 프로필 수정 - memberId={}", memberId);

        service.updateProfile(memberId, request);

        return ResponseEntity.ok(
                ResponseDto.of(HttpStatus.OK, "SUCCESS", "프로필이 수정되었습니다.")
        );
    }

    @DeleteMapping
    @Operation(
            summary = "프로필 삭제",
            description = "등록한 회사 프로필을 삭제합니다."
    )
    @ApiErrorCodeExamples({
            ErrorCode.ENTITY_NOT_FOUND,
            ErrorCode.AUTHENTICATION_FAILED
    })
    public ResponseEntity<ResponseDto<Void>> deleteProfile(
            @AuthenticationPrincipal UserAuthentication auth) {

        Long memberId = auth.getUserId();
        log.debug("[Linko Profile] 프로필 삭제 - memberId={}", memberId);

        service.deleteProfile(memberId);

        return ResponseEntity.ok(
                ResponseDto.of(HttpStatus.OK, "SUCCESS", "프로필이 삭제되었습니다.")
        );
    }
}