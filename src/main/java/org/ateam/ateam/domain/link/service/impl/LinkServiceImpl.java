package org.ateam.ateam.domain.link.service.impl;

import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.link.converter.LinkConverter;
import org.ateam.ateam.domain.link.dto.req.LinkReqDTO;
import org.ateam.ateam.domain.link.exception.LinkException;
import org.ateam.ateam.domain.link.repository.LinkRepository;
import org.ateam.ateam.domain.link.service.LinkService;
import org.ateam.ateam.domain.linker.model.entity.Linker;
import org.ateam.ateam.domain.linker.repository.LinkerRepository;
import org.ateam.ateam.domain.linko.model.Linko;
import org.ateam.ateam.domain.linko.repository.LinkoRepository;
import org.ateam.ateam.domain.member.enums.LinkTingRole;
import org.ateam.ateam.domain.member.exception.MemberException;
import org.ateam.ateam.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {
  private final LinkRepository linkRepository;
  private final LinkoRepository linkoRepository;
  private final LinkerRepository linkerRepository;

  @Transactional
  @Override
  public void doLink(LinkReqDTO.linkDTO dto, Long memberId) {
    Linker linker;
    Linko linko;

    if (dto.linkTingRole() == LinkTingRole.LINKER) {
      linker =
          linkerRepository
              .findByMemberId(memberId)
              .orElseThrow(() -> new MemberException(ErrorCode.USER_NOT_FOUND));

      linko =
          linkoRepository
              .findById(dto.linkoId())
              .orElseThrow(() -> new LinkException(ErrorCode.LINKO_NOT_FOUND));
    } else if (dto.linkTingRole() == LinkTingRole.LINKO) {
      linko =
          linkoRepository
              .findByMemberId(memberId)
              .orElseThrow(() -> new MemberException(ErrorCode.USER_NOT_FOUND));

      linker =
          linkerRepository
              .findById(dto.linkerId())
              .orElseThrow(() -> new LinkException(ErrorCode.LINKER_NOT_FOUND));
    } else {
      throw new LinkException(ErrorCode.LINKTINGROLE_NOT_FOUND);
    }

    // 2. (권장) 이미 링크가 존재하는지 중복 체크
    // Repository에 boolean existsByLinkerAndLinko(Linker linker, Linko linko); 메서드가 필요합니다.
    if (linkRepository.existsByLinkerAndLinko(linker, linko)) {
      throw new LinkException(ErrorCode.LINK_EXIST);
    }

    // 3. 링크 저장
    linkRepository.save(LinkConverter.toLinkEntity(linker, linko));
  }
}
