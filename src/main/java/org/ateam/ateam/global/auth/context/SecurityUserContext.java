package org.ateam.ateam.global.auth.context;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ateam.ateam.global.error.exception.UnauthorizedException;
import org.ateam.ateam.global.auth.security.UserAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityUserContext implements UserContext {

  /** 현재 인증된 User의 Id를 가져옵니다. */
  @Override
  public Long getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // 인증이 없으면 그냥 null 반환 (개발용)
    if (authentication == null) {
      log.warn("[SecurityUserContext] 인증 객체가 null -> 개발 모드로 null 반환");
      return null;
    }

    if (!(authentication instanceof UserAuthentication userAuth)) {
      log.warn("[SecurityUserContext] 인증 객체 타입 다름 -> 개발 모드로 null 반환");
      return null;
    }

    Long userId = userAuth.getUserId();

    if (userId == null) {
      log.warn("[SecurityUserContext] userId null -> 개발 모드로 null 반환");
      return null;
    }

    return userId;
  }
}