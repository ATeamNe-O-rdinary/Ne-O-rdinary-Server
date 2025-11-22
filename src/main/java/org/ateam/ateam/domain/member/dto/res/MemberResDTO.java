package org.ateam.ateam.domain.member.dto.res;

import lombok.Builder;

public class MemberResDTO {

  @Builder
  public record ProfileListDTO(Long memberId, String username, String profileImage) {}
}
