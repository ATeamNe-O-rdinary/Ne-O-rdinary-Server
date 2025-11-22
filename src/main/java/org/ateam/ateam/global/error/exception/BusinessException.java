package org.ateam.ateam.global.error.exception;

import org.ateam.ateam.global.error.ErrorCode;

public class BusinessException extends RuntimeException {

  private ErrorCode errorCode;

  public BusinessException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public BusinessException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
