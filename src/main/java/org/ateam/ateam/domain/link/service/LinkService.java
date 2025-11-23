package org.ateam.ateam.domain.link.service;

import org.ateam.ateam.domain.link.dto.req.LinkReqDTO;
import org.ateam.ateam.domain.link.dto.request.LinkRequest;
import org.springframework.transaction.annotation.Transactional;

public interface LinkService {

  @Transactional
  void doLink(LinkReqDTO.linkDTO dto, Long memberId);
}