package org.ateam.ateam.domain.member.validator;

import org.ateam.ateam.domain.member.entity.Member;
import org.ateam.ateam.domain.member.exception.MemberNotFoundException;
import org.ateam.ateam.domain.member.exception.ProfileAlreadyExistsException;
import org.ateam.ateam.domain.member.exception.profile.ProfileNotFoundException;
import org.ateam.ateam.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ProfileImageValidator {
	private final MemberRepository memberRepository;

	public void validateAlreadyExists(Member member) {
		if (member.getProfileImageUrl() != null) {
			throw new ProfileAlreadyExistsException();
		}
	}

	public void validateExists(Member member) {
		if (member.getProfileImageUrl() == null) {
			throw new ProfileNotFoundException();
		}
	}

	public Member getMemberOrThrow(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(MemberNotFoundException::new);
	}
}