package org.ateam.ateam.domain.member.exception;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

/** 카카오 서버 오류 예외 카카오 서버에서 5xx 오류가 발생하거나 네트워크 문제가 있을 때 발생 */
public class KakaoServerException extends BusinessException {

  public KakaoServerException() {
    super(ErrorCode.KAKAO_SERVER_ERROR);
  }

  public KakaoServerException(String message) {
    super(ErrorCode.KAKAO_SERVER_ERROR, message);
  }
}
