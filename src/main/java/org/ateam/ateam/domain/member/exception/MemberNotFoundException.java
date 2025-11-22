package org.ateam.ateam.domain.member.exception;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

public class MemberNotFoundException extends BusinessException {
	public MemberNotFoundException() {
		super(ErrorCode.USER_NOT_FOUND);
	}
}
