package org.ateam.ateam.domain.member.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompanyType {
  MANUFACTURING("제조업"),
  FOOD_CAFE("식당/카페"),
  FOOD_HEALTH("식품/건강"),
  IT_MEDIA("IT/미디어"),
  BEAUTY_FASHION("뷰티/패션"),
  HOSPITAL("병원"),
  SEMICONDUCTOR("반도체"),
  EDUCATION("교육/육아"),
  PUBLIC_INSTITUTION("공공기관"),
  DAILY_LIFE("일반/기타");

  private final String title;
}
