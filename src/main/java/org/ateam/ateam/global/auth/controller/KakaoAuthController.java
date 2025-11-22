package org.ateam.ateam.global.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ateam.ateam.domain.member.service.KakaoAuthService;
import org.ateam.ateam.global.auth.dto.KakaoLoginRequestDTO;
import org.ateam.ateam.global.auth.dto.LoginResponseDTO;
import org.ateam.ateam.global.auth.dto.UserAuthInfoDTO;
import org.ateam.ateam.global.auth.enums.Provider;
import org.ateam.ateam.global.auth.service.UserAuthService;
import org.ateam.ateam.global.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/kakao")
@Tag(name = "Kakao Login", description = "카카오 로그인 API")
public class KakaoAuthController {

  private final KakaoAuthService kakaoAuthService;
  private final UserAuthService userAuthService;

  @PostMapping("/login")
  @Operation(
      summary = "카카오 로그인",
      description =
          "카카오 서버로부터 발급받은 `Access Token`과 `Refresh Token`을 사용하여,  \n"
              + "서버에서 JWT를 발급받는 API입니다.  \n"
              + "* 이미 가입된 유저면 로그인 처리  \n"
              + "* 신규 유저면 회원가입 후 로그인 처리  \n"
              + "* Access Token은 응답 헤더의 `Authorization`에 포함됩니다.")
  public ResponseEntity<ResponseDto<LoginResponseDTO.LoginUserInfo>> kakaoLogin(
      @Valid @RequestBody KakaoLoginRequestDTO request) {

    // record 타입은 필드명으로 직접 접근 (getter 메서드 이름이 getAccessToken()이 아니라 accessToken())
    String accessToken = request.accessToken();
    String refreshToken = request.refreshToken();

    log.debug("[Kakao Login] 카카오 로그인 요청 수신");

    // 카카오 서버에서 사용자 정보 조회
    UserAuthInfoDTO userInfo = kakaoAuthService.getUserInfo(accessToken);

    // 회원 생성 또는 업데이트 및 JWT 발급
    LoginResponseDTO loginResponse =
        userAuthService.createOrUpdateUser(userInfo, Provider.KAKAO, refreshToken);

    log.info("[Kakao Login] 로그인 성공 - userId={}", loginResponse.getUserInfo().getUserId());

    // ResponseDto로 감싸기
    ResponseDto<LoginResponseDTO.LoginUserInfo> response =
        ResponseDto.of(HttpStatus.OK, null, "카카오 로그인에 성공했습니다.", loginResponse.getUserInfo());

    // Access Token을 헤더에 추가
    return ResponseEntity.ok()
        .header("Authorization", "Bearer " + loginResponse.getAccessToken())
        .header("X-Expires-In", String.valueOf(loginResponse.getExpiresIn()))
        .body(response);
  }
}
