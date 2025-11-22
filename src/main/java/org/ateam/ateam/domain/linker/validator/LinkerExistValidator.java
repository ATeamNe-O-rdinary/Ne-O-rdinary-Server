package org.ateam.ateam.domain.linker.validator;

import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.linker.exception.LinkerAlreadyExistsException;
import org.ateam.ateam.domain.linker.exception.LinkerNotFoundException;
import org.ateam.ateam.domain.linker.model.entity.Linker;
import org.ateam.ateam.domain.linker.repository.LinkerRepository;
import org.ateam.ateam.domain.member.entity.Member;
import org.ateam.ateam.domain.member.exception.MemberException;
import org.ateam.ateam.domain.member.repository.MemberRepository;
import org.ateam.ateam.global.error.ErrorCode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkerExistValidator {

	private final LinkerRepository linkerRepository;
	private final MemberRepository memberRepository;

	public Member getMemberOrThrow(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(ErrorCode.USER_NOT_FOUND));
	}

	public void validateLinkerNotExists(Long memberId) {
		if (linkerRepository.existsByMemberId(memberId)) {
			throw new LinkerAlreadyExistsException();
		}
	}

	public Linker getOwnedLinkerOrThrow(Long linkerId, Long memberId) {
		return linkerRepository.findByIdAndMemberId(linkerId, memberId)
			.orElseThrow(LinkerNotFoundException::new);
	}
}
