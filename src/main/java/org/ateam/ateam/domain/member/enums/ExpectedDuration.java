package org.ateam.ateam.domain.member.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExpectedDuration {
  LESS_THAN_ONE_MONTH("1개월 미만"),
  LESS_THAN_THREE_MONTHS("3개월 미만"),
  MORE_THAN_THREE_MONTHS("3개월 이상");

  private final String title;
}
