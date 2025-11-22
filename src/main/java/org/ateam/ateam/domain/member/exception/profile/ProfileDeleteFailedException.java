package org.ateam.ateam.domain.member.exception.profile;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

public class ProfileDeleteFailedException extends BusinessException {
	public ProfileDeleteFailedException() {
		super(ErrorCode.PROFILE_DELETE_FAILED);
	}
}
