package org.ateam.ateam.domain.linko.exception;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

/**
 * Linko 프로필 접근 권한 거부 예외
 * 다른 사용자의 프로필을 수정/삭제하려고 시도할 때 발생
 */
public class LinkoAccessDeniedException extends BusinessException {

    public LinkoAccessDeniedException() {
        super(ErrorCode.HANDLE_ACCESS_DENIED);
    }

    public LinkoAccessDeniedException(String message) {
        super(ErrorCode.HANDLE_ACCESS_DENIED, message);
    }
}