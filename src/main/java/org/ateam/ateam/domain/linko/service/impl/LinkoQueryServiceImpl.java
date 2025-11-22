package org.ateam.ateam.domain.linko.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.linko.controller.response.LinkoCardResponse;
import org.ateam.ateam.domain.linko.repository.LinkoRepository;
import org.ateam.ateam.domain.linko.service.LinkoQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LinkoQueryServiceImpl implements LinkoQueryService {

  private final LinkoRepository linkoRepository;

  @Override
  public List<LinkoCardResponse> getCards() {
    return linkoRepository.findAll().stream().map(LinkoCardResponse::of).toList();
  }
}
