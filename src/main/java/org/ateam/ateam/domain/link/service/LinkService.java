package org.ateam.ateam.domain.link.service;

import org.ateam.ateam.domain.link.dto.req.LinkReqDTO;

public interface LinkService {
  void doLink(LinkReqDTO.linkDTO dto);
}
