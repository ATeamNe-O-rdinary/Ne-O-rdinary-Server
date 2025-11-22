package org.ateam.ateam.domain.linker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.linker.controller.response.LinkerProfileSummaryResponse;
import org.ateam.ateam.domain.linker.service.LinkerProfileService;
import org.ateam.ateam.global.auth.context.UserContext;
import org.ateam.ateam.global.config.swagger.ApiErrorCodeExamples;
import org.ateam.ateam.global.dto.ResponseDto;
import org.ateam.ateam.global.error.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/linkers")
@Tag(name = "Linker Profile Summary", description = "링커 프로필 요약 조회 API")
public class LinkerProfileController {

  private final LinkerProfileService linkerProfileService;
  private final UserContext userContext;

  @Operation(summary = "내 링커 프로필 요약 조회")
  @ApiErrorCodeExamples({ErrorCode.LINKER_NOT_FOUND, ErrorCode.USER_NOT_FOUND})
  @GetMapping("/me/summary")
  public ResponseDto<LinkerProfileSummaryResponse> getMyProfileSummary() {

    Long memberId = userContext.getCurrentUserId();
    LinkerProfileSummaryResponse response = linkerProfileService.getMyProfileSummary(memberId);

    return ResponseDto.of(
        HttpStatus.OK, "LINKER_PROFILE_SUMMARY_SUCCESS", "링커 프로필 요약 조회 성공", response);
  }
}
