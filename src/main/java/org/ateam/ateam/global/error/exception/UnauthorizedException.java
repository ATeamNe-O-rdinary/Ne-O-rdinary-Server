package org.ateam.ateam.global.error.exception;

import org.ateam.ateam.global.error.ErrorCode;

public class UnauthorizedException extends BusinessException {
  public UnauthorizedException() {
    super(ErrorCode.AUTHENTICATION_FAILED);
  }
}
