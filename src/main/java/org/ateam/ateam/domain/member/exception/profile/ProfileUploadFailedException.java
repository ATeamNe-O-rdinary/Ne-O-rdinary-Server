package org.ateam.ateam.domain.member.exception.profile;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

public class ProfileUploadFailedException extends BusinessException {
	public ProfileUploadFailedException() {
		super(ErrorCode.PROFILE_UPLOAD_FAILED);
	}
}
