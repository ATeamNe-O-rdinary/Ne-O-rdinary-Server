package org.ateam.ateam.domain.link.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.link.dto.request.LinkRequest;
import org.ateam.ateam.domain.link.service.LinkService;
import org.ateam.ateam.global.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Link", description = "링크 관리 API")
public class LinkController {

  private final LinkService linkService;

  @Operation(summary = "링크 요청")
  @PostMapping("/api/v1/links")
  public ResponseDto<Void> doLink(@RequestBody LinkRequest.linkDTO dto) {
    linkService.doLink(dto);
    return ResponseDto.of(
        HttpStatus.OK,
        "LINK_SUCCESS",
        "링크 성공",
        null
    );
  }
}
