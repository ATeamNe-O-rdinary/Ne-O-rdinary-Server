package org.ateam.ateam.domain.linker.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RateUnit {
	HOURLY("시급"),
	PER_CASE("건수"),
	MONTHLY("월별");

	private final String title;
}
