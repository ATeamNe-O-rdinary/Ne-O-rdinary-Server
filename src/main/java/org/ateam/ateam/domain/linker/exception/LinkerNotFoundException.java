package org.ateam.ateam.domain.linker.exception;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

public class LinkerNotFoundException extends BusinessException {
  public LinkerNotFoundException() {
    super(ErrorCode.LINKER_NOT_FOUND);
  }
}
