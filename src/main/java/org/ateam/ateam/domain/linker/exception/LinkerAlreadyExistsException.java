package org.ateam.ateam.domain.linker.exception;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

public class LinkerAlreadyExistsException extends BusinessException {
  public LinkerAlreadyExistsException() {
    super(ErrorCode.LINKER_ALREADY_EXISTS);
  }
}
