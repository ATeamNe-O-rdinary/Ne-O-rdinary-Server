package org.ateam.ateam.domain.member.exception;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

public class ProfileAlreadyExistsException extends BusinessException {
  public ProfileAlreadyExistsException() {
    super(ErrorCode.PROFILE_ALREADY_EXISTS);
  }
}
