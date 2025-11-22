package org.ateam.ateam.domain.link.exception;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

public class LinkException extends BusinessException {
    public LinkException(ErrorCode errorCode) {
        super(errorCode);
    }

    public LinkException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
