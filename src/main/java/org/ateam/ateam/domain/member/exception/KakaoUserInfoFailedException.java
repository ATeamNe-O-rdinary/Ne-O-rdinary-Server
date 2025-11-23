package org.ateam.ateam.domain.member.exception;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

/** 카카오 사용자 정보 조회 실패 예외 카카오 API에서 사용자 정보를 가져올 수 없을 때 발생 */
public class KakaoUserInfoFailedException extends BusinessException {


  public KakaoUserInfoFailedException(String message) {
    super(ErrorCode.KAKAO_USER_INFO_FAILED, message);
  }
}
