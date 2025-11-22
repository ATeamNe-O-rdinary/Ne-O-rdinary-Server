package org.ateam.ateam.domain.linker.exception;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

public class InvalidLinkerEnumException extends BusinessException {
  public InvalidLinkerEnumException() {
    super(ErrorCode.LINKER_INVALID_ENUM);
  }
}
