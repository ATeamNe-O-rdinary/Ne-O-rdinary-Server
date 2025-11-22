package org.ateam.ateam.domain.linker.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkTimeType {
	WEEKEND("주말"),
	WEEKDAY_EVENING("평일 저녁"),
	ANYTIME("상시 가능");

	private final String title;
}
