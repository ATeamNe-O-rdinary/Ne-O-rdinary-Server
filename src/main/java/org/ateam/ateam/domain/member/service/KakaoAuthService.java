package org.ateam.ateam.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.ateam.ateam.domain.member.exception.InvalidKakaoTokenException;
import org.ateam.ateam.domain.member.exception.KakaoServerException;
import org.ateam.ateam.domain.member.exception.KakaoUserInfoFailedException;
import org.ateam.ateam.domain.member.repository.SocialAuthRepository;
import org.ateam.ateam.global.auth.dto.KakaoTokenResponseDTO;
import org.ateam.ateam.global.auth.dto.KakaoUserInfoResponseDTO;
import org.ateam.ateam.global.auth.dto.UserAuthInfoDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class KakaoAuthService {

  private final SocialAuthRepository socialAuthRepository;
  private final ObjectMapper objectMapper;
  private final WebClient kakaoWebClient;
  private final WebClient kakaoTokenWebClient;

  @Value("${auth.kakao.native-app-key}")
  private String kakaoNativeAppKey;

  public KakaoAuthService(
      SocialAuthRepository socialAuthRepository,
      ObjectMapper objectMapper,
      @Qualifier("kakaoWebClient") WebClient kakaoWebClient,
      @Qualifier("kakaoTokenWebClient") WebClient kakaoTokenWebClient) {
    this.socialAuthRepository = socialAuthRepository;
    this.objectMapper = objectMapper;
    this.kakaoWebClient = kakaoWebClient;
    this.kakaoTokenWebClient = kakaoTokenWebClient;
  }

  /** 카카오 회원의 정보를 가져옵니다. */
  public UserAuthInfoDTO getUserInfo(String accessToken) {
    KakaoUserInfoResponseDTO userInfo =
        kakaoWebClient
            .get()
            .uri("/v2/user/me")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                response ->
                    response
                        .bodyToMono(String.class)
                        .map(
                            errorBody -> {
                              log.error("[Kakao Service] API 에러 응답: {}", errorBody);
                              // 커스텀 예외 던지기
                              handleKakaoApiError(errorBody);
                              // 여기 도달하지 않음 (handleKakaoApiError에서 예외 던짐)
                              return new RuntimeException("카카오 API 실패");
                            }))
            .bodyToMono(KakaoUserInfoResponseDTO.class)
            .block();

    // 디버깅: 카카오 응답 확인
    log.debug("[Kakao Service] raw 응답: {}", toJson(userInfo));

    validateKakaoUserInfo(userInfo);

    // 이메일과 닉네임 안전하게 추출
    String email = null;
    String nickname = "카카오 사용자"; // 기본값

    if (userInfo.getKakaoAccount() != null) {
      email = userInfo.getKakaoAccount().getEmail();
      if (userInfo.getKakaoAccount().getProfile() != null
          && userInfo.getKakaoAccount().getProfile().getNickName() != null) {
        nickname = userInfo.getKakaoAccount().getProfile().getNickName();
      }
    }

    return UserAuthInfoDTO.builder()
        .providerId(userInfo.getId().toString())
        .email(email)
        .username(nickname)
        .build();
  }

  /** refreshToken을 사용하여 새로운 accessToken을 발급합니다. */
  public String refreshAccessToken(Long userId) {
    String refreshToken =
        socialAuthRepository
            .findRefreshTokenByUserId(userId)
            .orElseThrow(() -> new InvalidKakaoTokenException("Refresh token을 찾을 수 없습니다."));

    KakaoTokenResponseDTO tokenResponse =
        kakaoTokenWebClient
            .post()
            .uri(
                uriBuilder ->
                    uriBuilder
                        .path("/oauth/token")
                        .queryParam("grant_type", "refresh_token")
                        .queryParam("client_id", kakaoNativeAppKey)
                        .queryParam("refresh_token", refreshToken)
                        .build())
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                response ->
                    response
                        .bodyToMono(String.class)
                        .map(
                            errorBody -> {
                              log.error("[Kakao Service] API 에러 응답: {}", errorBody);
                              handleKakaoApiError(errorBody);
                              return new RuntimeException("카카오 API 실패");
                            }))
            .bodyToMono(KakaoTokenResponseDTO.class)
            .block();
    log.debug("[Kakao Service] raw 응답: {}", toJson(tokenResponse));

    if (tokenResponse == null || tokenResponse.getAccessToken() == null) {
      throw new KakaoServerException("카카오 액세스 토큰 갱신에 실패했습니다.");
    }

    if (tokenResponse.getRefreshToken() != null) {
      updateRefreshTokenInDB(userId, tokenResponse.getRefreshToken());
    }

    return tokenResponse.getAccessToken();
  }

  /** user_auth 테이블의 refreshToken을 업데이트합니다. */
  private void updateRefreshTokenInDB(Long userId, String newRefreshToken) {
    socialAuthRepository.updateRefreshToken(userId, newRefreshToken);
    log.debug("[Kakao Service] Refresh token updated");
  }

  /** 카카오 사용자 정보가 null 인지 검사합니다. */
  private void validateKakaoUserInfo(KakaoUserInfoResponseDTO userInfo) {
    log.debug("[Kakao Validation] userInfo: {}", userInfo);

    if (userInfo == null || userInfo.getId() == null) {
      // 커스텀 예외
      throw new KakaoUserInfoFailedException("카카오 계정의 고유 ID(auth_id)가 존재하지 않습니다.");
    }

    // 카카오 고유 ID는 항상 제공됨
    log.debug("[Kakao Validation] 카카오 고유 ID 확인 - id: {}", userInfo.getId());

    // 이메일과 닉네임은 선택사항 (동의항목 설정에 따라 null 가능)
    if (userInfo.getKakaoAccount() != null) {
      String email = userInfo.getKakaoAccount().getEmail();
      String nickname =
          userInfo.getKakaoAccount().getProfile() != null
              ? userInfo.getKakaoAccount().getProfile().getNickName()
              : null;

      log.debug("[Kakao Validation] 선택 정보 - email: {}, nickname: {}", email, nickname);
    } else {
      log.warn("[Kakao Validation] kakaoAccount 정보 없음 (동의항목 미설정)");
    }
  }

  /** 예외 처리 - 커스텀 예외로 변경 */
  private void handleKakaoApiError(String errorResponse) {
    log.error("[Kakao API Error] {}", errorResponse);

    // -401: 유효하지 않은 토큰
    if (errorResponse.contains("\"code\":-401") || errorResponse.contains("\"code\": -401")) {
      throw new InvalidKakaoTokenException("카카오 액세스 토큰이 만료되었거나 유효하지 않습니다.");
    }

    // -402: API 사용량 초과
    if (errorResponse.contains("\"code\":-402") || errorResponse.contains("\"code\": -402")) {
      throw new KakaoServerException("카카오 API 사용량 초과. 잠시 후 다시 시도해주세요.");
    }

    // -500: 카카오 서버 내부 오류
    if (errorResponse.contains("\"code\":-500") || errorResponse.contains("\"code\": -500")) {
      throw new KakaoServerException("카카오 서버 내부 오류가 발생했습니다.");
    }

    // 기타 에러
    throw new KakaoServerException("카카오 API 요청 실패: " + errorResponse);
  }

  private String toJson(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      return "[json 변환 실패]";
    }
  }

  /** 카카오 계정 연결 끊기(회원탈퇴) */
  public boolean disconnectKakao(String accessToken) {
    try {
      kakaoWebClient
          .post()
          .uri("/v1/user/unlink")
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
          .retrieve()
          .onStatus(
              HttpStatusCode::isError,
              response ->
                  response
                      .bodyToMono(String.class)
                      .map(
                          errorBody -> {
                            log.error("[Kakao disconnect] API 에러 응답: {}", errorBody);
                            throw new KakaoServerException("카카오 계정 연결 해제 실패: " + errorBody);
                          }))
          .bodyToMono(Void.class)
          .block();

      log.info("[Kakao disconnect] 카카오 계정 연결 해제 성공");
      return true;
    } catch (Exception e) {
      log.error("[Kakao disconnect] 카카오 계정 연결 해제 중 예외 발생", e);
      return false;
    }
  }
}
