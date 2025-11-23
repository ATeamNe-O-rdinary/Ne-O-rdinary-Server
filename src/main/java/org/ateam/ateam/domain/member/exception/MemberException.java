package org.ateam.ateam.domain.member.exception;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

public class MemberException extends BusinessException {
  public MemberException(ErrorCode errorCode) {
    super(errorCode);
  }
}
