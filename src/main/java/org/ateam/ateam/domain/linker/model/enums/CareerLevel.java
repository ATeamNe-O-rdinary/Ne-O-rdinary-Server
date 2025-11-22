package org.ateam.ateam.domain.linker.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CareerLevel {
	JUNIOR("초급"),
	MID("중급"),
	SENIOR("시니어");

	private final String title;
}
