package org.ateam.ateam.global.auth.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ateam.ateam.domain.member.entity.Member;
import org.ateam.ateam.domain.member.entity.SocialAuth;
import org.ateam.ateam.domain.member.repository.MemberRepository;
import org.ateam.ateam.domain.member.repository.SocialAuthRepository;
import org.ateam.ateam.global.auth.dto.LoginResponseDTO;
import org.ateam.ateam.global.auth.dto.UserAuthInfoDTO;
import org.ateam.ateam.global.auth.enums.Provider;
import org.ateam.ateam.global.auth.token.JwtTokenProvider;
import org.ateam.ateam.global.security.UserAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserAuthServiceImpl implements UserAuthService {

  private final MemberRepository memberRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final SocialAuthRepository socialAuthRepository;

  @Transactional
  @Override
  public LoginResponseDTO createOrUpdateUser(
      UserAuthInfoDTO userInfo, Provider provider, String refreshToken) {
    AtomicBoolean isNewUser = new AtomicBoolean(false);

    String providerId = userInfo.getProviderId();
    String email = userInfo.getEmail(); // 이메일은 선택사항 (null 가능)
    String newUsername = userInfo.getUsername();

    log.debug("[AUTH] 카카오 로그인 시도 - providerId={}, email={}", providerId, email);

    // 1. Provider ID로 먼저 조회 (중복 가입 방지) - 핵심!
    Optional<SocialAuth> optionalSocialAuth =
        socialAuthRepository.findByProviderIdAndProvider(providerId, provider);

    SocialAuth socialAuth;
    Member member;

    // 2. 기존 소셜 인증 정보가 있는 경우 (기존 회원)
    if (optionalSocialAuth.isPresent()) {
      socialAuth = optionalSocialAuth.get();
      member = socialAuth.getMember();

      log.debug("[AUTH] 기존 회원 로그인 - memberId={}, email={}", member.getId(), email);

      // Refresh Token 갱신
      if (refreshToken != null
          && (socialAuth.getRefreshToken() == null
              || !refreshToken.equals(socialAuth.getRefreshToken()))) {
        log.debug("[AUTH] Refresh Token 갱신");
        LocalDateTime tokenExpiry = LocalDateTime.now().plusDays(60);
        socialAuth.updateRefreshToken(refreshToken, tokenExpiry);
        socialAuthRepository.save(socialAuth);
      }

      // Username 변경 처리
      if (newUsername != null && !newUsername.equals(member.getUsername())) {
        log.debug("[AUTH] Username 변경: {} -> {}", member.getUsername(), newUsername);
        member.setUsername(newUsername);
        memberRepository.save(member);
      }

      // 탈퇴했던 회원이면 재활성화
      if (member.isDeleted()) {
        log.info("[AUTH] 탈퇴 회원 재활성화 - memberId={}", member.getId());
        member.reactivate();
        memberRepository.save(member);
      }

      // 인증 객체 등록
      setAuthentication(member);

      return generateLoginResponse(member, false);
    }

    // 3. 이메일로 기존 회원 조회 (다른 Provider로 가입했을 수 있음)
    Optional<Member> optionalMember = Optional.empty();
    if (email != null && !email.isEmpty()) {
      optionalMember = memberRepository.findByEmail(email);
    }

    if (optionalMember.isPresent()) {
      // 같은 이메일로 이미 가입된 회원이 있음
      member = optionalMember.get();
      log.debug(
          "[AUTH] 동일 이메일 회원 존재 - memberId={}, 기존 Provider={}, 신규 Provider={}",
          member.getId(),
          member.getLoginProvider(),
          provider);

      // 탈퇴 회원이면 재활성화
      if (member.isDeleted()) {
        log.info("[AUTH] 탈퇴 회원 재활성화 - memberId={}", member.getId());
        member.reactivate();
        memberRepository.save(member);
      }
    } else {
      // 4. 완전히 새로운 회원
      isNewUser.set(true);
      member = Member.builder().username(newUsername).email(email).loginProvider(provider).build();
      member = memberRepository.save(member);
      log.info("[AUTH] 신규 회원 생성 - memberId={}, email={}", member.getId(), email);
    }

    // 5. SocialAuth 정보 생성
    LocalDateTime tokenExpiry = LocalDateTime.now().plusDays(60);
    socialAuth =
        SocialAuth.builder()
            .member(member)
            .provider(provider)
            .providerId(providerId)
            .refreshToken(refreshToken)
            .tokenExpiry(tokenExpiry)
            .build();
    socialAuthRepository.save(socialAuth);
    log.debug("[AUTH] SocialAuth 정보 저장 완료 - provider={}, providerId={}", provider, providerId);

    // 인증 객체 등록
    setAuthentication(member);

    return generateLoginResponse(member, isNewUser.get());
  }

  /** 인증 객체를 설정합니다. */
  private void setAuthentication(Member member) {
    Authentication authentication = new UserAuthentication(member.getId(), null, null);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    log.info("[AUTH] SecurityContext에 인증 객체 설정 완료 - memberId={}", member.getId());
  }

  /** JWT 발급 및 응답 생성 */
  private LoginResponseDTO generateLoginResponse(Member member, boolean isNewUser) {
    Long memberId = member.getId();

    String accessToken = jwtTokenProvider.generateAccessToken(memberId);
    Long expiresIn = jwtTokenProvider.getAccessTokenExpirySeconds();

    log.info("[LOGIN] 토큰 발급 완료 - memberId={}, isNewUser={}", memberId, isNewUser);

    LoginResponseDTO.LoginUserInfo loginUserInfo =
        LoginResponseDTO.LoginUserInfo.builder()
            .userId(memberId)
            .username(member.getUsername())
            .isNewUser(isNewUser)
            .build();

    return LoginResponseDTO.builder()
        .accessToken(accessToken)
        .expiresIn(expiresIn)
        .userInfo(loginUserInfo)
        .build();
  }
}
