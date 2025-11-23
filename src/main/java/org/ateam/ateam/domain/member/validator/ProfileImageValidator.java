package org.ateam.ateam.domain.member.validator;

import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.member.entity.member.Member;
import org.ateam.ateam.domain.member.exception.MemberNotFoundException;
import org.ateam.ateam.domain.member.exception.profile.ProfileAlreadyExistsException;
import org.ateam.ateam.domain.member.exception.profile.ProfileNotFoundException;
import org.ateam.ateam.domain.member.repository.member.MemberRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProfileImageValidator {
  private final MemberRepository memberRepository;

  public void validateAlreadyExists(Member member) {
    if (member.getProfileImageUrl() != null) {
      throw new ProfileAlreadyExistsException();
    }
  }

  public void validateExists(Member member) {
    if (member.getProfileImageUrl() == null) {
      throw new ProfileNotFoundException();
    }
  }

  public Member getMemberOrThrow(Long memberId) {
    return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
  }
}
