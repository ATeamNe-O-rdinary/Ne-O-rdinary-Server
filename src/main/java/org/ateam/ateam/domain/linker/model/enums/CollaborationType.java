package org.ateam.ateam.domain.linker.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CollaborationType {
  ONLINE("온라인"),
  OFFLINE("오프라인"),
  BOTH("온라인/오프라인");

  private final String title;
}
