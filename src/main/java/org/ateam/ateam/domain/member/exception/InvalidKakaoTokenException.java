package org.ateam.ateam.domain.member.exception;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

/** 유효하지 않은 카카오 액세스 토큰 예외 카카오 토큰이 만료되었거나 잘못된 경우 발생 */
public class InvalidKakaoTokenException extends BusinessException {

  public InvalidKakaoTokenException(String message) {
    super(ErrorCode.KAKAO_TOKEN_INVALID, message);
  }
}
