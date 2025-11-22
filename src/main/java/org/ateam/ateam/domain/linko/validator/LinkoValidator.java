package org.ateam.ateam.domain.linko.validator;

import lombok.RequiredArgsConstructor;

import org.ateam.ateam.domain.linko.exception.LinkoNotFoundException;
import org.ateam.ateam.domain.linko.model.Linko;
import org.ateam.ateam.domain.linko.repository.LinkoRepository;
import org.ateam.ateam.domain.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkoValidator {

	private final LinkoRepository linkoRepository;

	public Linko getByMemberOrThrow(Member member) {
		return linkoRepository.findByMember(member)
			.orElseThrow(LinkoNotFoundException::new);
	}
}
