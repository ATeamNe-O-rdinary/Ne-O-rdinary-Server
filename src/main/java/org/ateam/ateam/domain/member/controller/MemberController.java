package org.ateam.ateam.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    //메인화면 프로필 리스트 (슬라이드)
    @GetMapping("/api/members")
    public ResponseEntity<> getMemberList(){

    }
}
