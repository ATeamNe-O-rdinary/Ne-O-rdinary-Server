package org.ateam.ateam.domain.member.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MainCategory {
  DESIGN("디자인"),
  MARKETING("마케팅"),
  IT_PROGRAMMING("IT프로그래밍");

  private final String title;
}
