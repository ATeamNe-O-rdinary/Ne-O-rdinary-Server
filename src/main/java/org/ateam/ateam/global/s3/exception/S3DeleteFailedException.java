package org.ateam.ateam.global.s3.exception;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

public class S3DeleteFailedException extends BusinessException {
  public S3DeleteFailedException() {
    super(ErrorCode.S3_DELETE_FAILED);
  }
}
