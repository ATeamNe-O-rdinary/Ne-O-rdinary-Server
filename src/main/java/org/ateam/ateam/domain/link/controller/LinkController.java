package org.ateam.ateam.domain.link.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.link.dto.req.LinkReqDTO;
import org.ateam.ateam.domain.link.service.LinkService;
import org.ateam.ateam.global.auth.context.UserContext;
import org.ateam.ateam.global.config.swagger.ApiErrorCodeExamples;
import org.ateam.ateam.global.dto.ResponseDto;
import org.ateam.ateam.global.error.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LinkController {
  private final LinkService linkService;
  private final UserContext userContext;

  @Operation(
      summary = "링크(매칭) 신청",
      description =
          """
            로그인한 사용자가 상대방에게 링크를 신청합니다.

            - **요청자가 LINKER인 경우**: 본인(Linker) -> 상대방(Linko ID 입력 필요)
            - **요청자가 LINKO인 경우**: 본인(Linko) -> 상대방(Linker ID 입력 필요)

            *입력된 Role에 따라 본인의 ID는 토큰에서 추출하여 자동 설정됩니다.*
            """)
  @ApiErrorCodeExamples({
    ErrorCode.USER_NOT_FOUND,
    ErrorCode.LINKER_NOT_FOUND,
    ErrorCode.LINKO_NOT_FOUND
  })
  @PostMapping("/api/links")
  public ResponseDto<Void> doLink(@RequestBody LinkReqDTO.linkDTO dto) {
    Long userId = userContext.getCurrentUserId();
    linkService.doLink(dto, userId);
    return ResponseDto.of(HttpStatus.OK, null, "링크 성공", null);
  }
}
