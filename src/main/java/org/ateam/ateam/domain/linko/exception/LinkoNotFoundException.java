package org.ateam.ateam.domain.linko.exception;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

public class LinkoNotFoundException extends BusinessException {
  public LinkoNotFoundException() {
    super(ErrorCode.LINKO_NOT_FOUND);
  }
}
