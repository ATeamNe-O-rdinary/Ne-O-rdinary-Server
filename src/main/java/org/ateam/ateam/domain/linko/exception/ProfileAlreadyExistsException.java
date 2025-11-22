package org.ateam.ateam.domain.linko.exception;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

/** 이미 등록된 Linko 프로필이 있을 때 발생하는 예외 */
public class ProfileAlreadyExistsException extends BusinessException {

  public ProfileAlreadyExistsException() {
    super(ErrorCode.PROFILE_ALREADY_EXISTS);
  }

  public ProfileAlreadyExistsException(String message) {
    super(ErrorCode.PROFILE_ALREADY_EXISTS, message);
  }
}
