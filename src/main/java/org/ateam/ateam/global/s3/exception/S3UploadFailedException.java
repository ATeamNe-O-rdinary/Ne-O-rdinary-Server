package org.ateam.ateam.global.s3.exception;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

public class S3UploadFailedException extends BusinessException {
  public S3UploadFailedException() {
    super(ErrorCode.S3_UPLOAD_FAILED);
  }
}
