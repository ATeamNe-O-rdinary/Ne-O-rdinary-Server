package org.ateam.ateam.domain.member.exception;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

/**
 * 회원 정보를 찾을 수 없을 때 발생하는 예외
 */
public class MemberNotFoundException extends BusinessException {

    public MemberNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }

    public MemberNotFoundException(String message) {
        super(ErrorCode.USER_NOT_FOUND, message);
    }
}