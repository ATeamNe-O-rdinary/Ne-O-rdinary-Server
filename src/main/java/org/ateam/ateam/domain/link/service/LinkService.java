package org.ateam.ateam.domain.link.service;

import org.ateam.ateam.domain.link.dto.request.LinkRequest;

public interface LinkService {
  void doLink(LinkRequest.linkDTO dto);
}
