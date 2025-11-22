package org.ateam.ateam.global.auth.service;

import org.ateam.ateam.global.auth.dto.login.response.LoginResponseDTO;
import org.ateam.ateam.global.auth.dto.UserAuthInfoDTO;
import org.ateam.ateam.global.auth.enums.Provider;

public interface UserAuthService {

  // 새로운 회윈 생성 또는 갱신
  LoginResponseDTO createOrUpdateUser(
      UserAuthInfoDTO userInfo, Provider authProvider, String refreshToken);
}
