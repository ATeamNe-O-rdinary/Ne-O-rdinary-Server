package org.ateam.ateam.domain.member.exception.profile;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

public class ProfileNotFoundException extends BusinessException {
	public ProfileNotFoundException() {
		super(ErrorCode.PROFILE_NOT_FOUND);
	}
}
