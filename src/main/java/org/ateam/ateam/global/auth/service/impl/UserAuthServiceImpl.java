/**
 * 카카오 유저 검증 및 토큰발급, 회원가입등
 */

package org.ateam.ateam.global.auth.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ateam.ateam.domain.member.entity.member.Member;
import org.ateam.ateam.domain.member.entity.SocialAuth;
import org.ateam.ateam.domain.member.repository.member.MemberRepository;
import org.ateam.ateam.domain.member.repository.social.SocialAuthRepository;
import org.ateam.ateam.global.auth.dto.login.response.LoginResponseDTO;
import org.ateam.ateam.global.auth.dto.UserAuthInfoDTO;
import org.ateam.ateam.global.auth.enums.Provider;
import org.ateam.ateam.global.auth.service.UserAuthService;
import org.ateam.ateam.global.auth.token.JwtTokenProvider;
import org.ateam.ateam.global.auth.security.UserAuthentication;
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
    String email = userInfo.getEmail();
    String newUsername = userInfo.getUsername();

    log.debug("[AUTH] 카카오 로그인 시도 - providerId={}, email={}", providerId, email);

    Optional<SocialAuth> optionalSocialAuth =
        socialAuthRepository.findByProviderIdAndProvider(providerId, provider);

    SocialAuth socialAuth;
    Member member;

    if (optionalSocialAuth.isPresent()) {
      socialAuth = optionalSocialAuth.get();
      member = socialAuth.getMember();

      log.debug("[AUTH] 기존 회원 로그인 - memberId={}, email={}", member.getId(), email);

      if (refreshToken != null
          && (socialAuth.getRefreshToken() == null
              || !refreshToken.equals(socialAuth.getRefreshToken()))) {
        log.debug("[AUTH] Refresh Token 갱신");
        LocalDateTime tokenExpiry = LocalDateTime.now().plusDays(60);
        socialAuth.updateRefreshToken(refreshToken, tokenExpiry);
        socialAuthRepository.save(socialAuth);
      }

      if (newUsername != null && !newUsername.equals(member.getUsername())) {
        log.debug("[AUTH] Username 변경: {} -> {}", member.getUsername(), newUsername);
        member.setUsername(newUsername);
        memberRepository.save(member);
      }

      if (member.isDeleted()) {
        log.info("[AUTH] 탈퇴 회원 재활성화 - memberId={}", member.getId());
        member.reactivate();
        memberRepository.save(member);
      }

      setAuthentication(member);

      return generateLoginResponse(member, false);
    }

    Optional<Member> optionalMember = Optional.empty();
    if (email != null && !email.isEmpty()) {
      optionalMember = memberRepository.findByEmail(email);
    }

    if (optionalMember.isPresent()) {
      member = optionalMember.get();
      log.debug(
          "[AUTH] 동일 이메일 회원 존재 - memberId={}, 기존 Provider={}, 신규 Provider={}",
          member.getId(),
          member.getLoginProvider(),
          provider);

      if (member.isDeleted()) {
        log.info("[AUTH] 탈퇴 회원 재활성화 - memberId={}", member.getId());
        member.reactivate();
        memberRepository.save(member);
      }
    } else {
      isNewUser.set(true);
      member = Member.builder().username(newUsername).email(email).loginProvider(provider).build();
      member = memberRepository.save(member);
      log.info("[AUTH] 신규 회원 생성 - memberId={}, email={}", member.getId(), email);
    }

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

    setAuthentication(member);

    return generateLoginResponse(member, isNewUser.get());
  }

  private void setAuthentication(Member member) {
    Authentication authentication = new UserAuthentication(member.getId(), null, null);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    log.info("[AUTH] SecurityContext에 인증 객체 설정 완료 - memberId={}", member.getId());
  }

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
