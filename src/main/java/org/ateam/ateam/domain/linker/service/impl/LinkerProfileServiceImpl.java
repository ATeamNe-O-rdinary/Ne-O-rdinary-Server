package org.ateam.ateam.domain.linker.service.impl;

import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.linker.controller.response.LinkerProfileSummaryResponse;
import org.ateam.ateam.domain.linker.exception.LinkerNotFoundException;
import org.ateam.ateam.domain.linker.model.entity.Linker;
import org.ateam.ateam.domain.linker.repository.LinkerRepository;
import org.ateam.ateam.domain.linker.service.LinkerProfileService;
import org.ateam.ateam.domain.member.entity.Member;
import org.ateam.ateam.domain.member.exception.MemberNotFoundException;
import org.ateam.ateam.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LinkerProfileServiceImpl implements LinkerProfileService {

  private final MemberRepository memberRepository;
  private final LinkerRepository linkerRepository;

  @Override
  public LinkerProfileSummaryResponse getMyProfileSummary(Long memberId) {
    Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

    Linker linker =
        linkerRepository.findByMemberId(memberId).orElseThrow(LinkerNotFoundException::new);

    return LinkerProfileSummaryResponse.of(linker, member);
  }
}
