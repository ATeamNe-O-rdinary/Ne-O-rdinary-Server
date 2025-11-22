package org.ateam.ateam.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.member.dto.req.MemberReqDTO;
import org.ateam.ateam.domain.member.dto.res.MemberResDTO;
import org.ateam.ateam.domain.member.service.MemberService;
import org.ateam.ateam.global.dto.PagedResponse;
import org.ateam.ateam.global.dto.ResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
  private final MemberService memberService;

  // 메인화면 프로필 리스트 (슬라이드)
  @GetMapping("/api/members/profiles")
  public ResponseDto<PagedResponse<MemberResDTO.ProfileListDTO>> getMemberList(
      @Valid @ModelAttribute MemberReqDTO.ProfileListDTO dto, Pageable pageable) {
    PagedResponse<MemberResDTO.ProfileListDTO> result = memberService.getProfileList(dto, pageable);
    return ResponseDto.of(HttpStatus.OK, null, "회원 프로필 목록 조회 성공", result);
  }
}
