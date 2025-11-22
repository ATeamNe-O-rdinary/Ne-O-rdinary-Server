package org.ateam.ateam.global.s3.exception;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

public class InvalidS3UrlException extends BusinessException {
  public InvalidS3UrlException() {
    super(ErrorCode.S3_INVALID_URL);
  }
}
