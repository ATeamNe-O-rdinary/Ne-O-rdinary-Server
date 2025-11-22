package org.ateam.ateam.domain.member.exception;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

public class LinkoProfileNotFoundException extends BusinessException {
    public LinkoProfileNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND, "등록된 프로필을 찾을 수 없습니다.");
    }
}