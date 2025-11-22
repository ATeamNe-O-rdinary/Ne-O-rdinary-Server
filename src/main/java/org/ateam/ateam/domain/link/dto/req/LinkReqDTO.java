package org.ateam.ateam.domain.link.dto.req;

import org.ateam.ateam.domain.member.enums.LinkTingRole;

public class LinkReqDTO {
  public record linkDTO(Long linkerId, Long linkoId, LinkTingRole linkTingRole) {}
}
