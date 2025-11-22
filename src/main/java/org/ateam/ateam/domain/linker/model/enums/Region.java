package org.ateam.ateam.domain.linker.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Region {
	SEOUL("서울"),
	GYEONGGI("경기"),
	INCHEON("인천"),
	DAEJEON("대전"),
	SEJONG("세종"),
	CHUNGBUK("충북"),
	CHUNGNAM("충남"),
	GWANGJU("광주"),
	JEONBUK("전북"),
	JEONNAM("전남"),
	DAEGU("대구"),
	GYEONGBUK("경북"),
	BUSAN("부산"),
	ULSAN("울산"),
	GYEONGNAM("경남"),
	GANGWON("강원"),
	JEJU("제주"),
	ANYWHERE("전국 무관");

	private final String title;
}
