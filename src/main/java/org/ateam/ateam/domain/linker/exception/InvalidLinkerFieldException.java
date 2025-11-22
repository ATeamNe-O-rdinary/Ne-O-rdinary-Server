package org.ateam.ateam.domain.linker.exception;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

public class InvalidLinkerFieldException extends BusinessException {
	public InvalidLinkerFieldException() {
		super(ErrorCode.LINKER_INVALID_FIELD);
	}
}
