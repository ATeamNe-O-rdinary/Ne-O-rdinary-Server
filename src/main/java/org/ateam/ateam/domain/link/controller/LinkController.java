package org.ateam.ateam.domain.link.controller;

import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.link.dto.req.LinkReqDTO;
import org.ateam.ateam.domain.link.service.LinkService;
import org.ateam.ateam.global.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LinkController {
  private final LinkService linkService;

  @PostMapping("/api/links")
  public ResponseDto<Void> doLink(@RequestBody LinkReqDTO.linkDTO dto) {
    linkService.doLink(dto);
    return ResponseDto.of(HttpStatus.OK, null, "링크 성공", null);
  }
}
