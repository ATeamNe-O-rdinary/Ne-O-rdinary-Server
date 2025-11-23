package org.ateam.ateam.domain.linker.controller.response;

import lombok.Builder;
import lombok.Getter;
import org.ateam.ateam.domain.linker.model.entity.Linker;
import org.ateam.ateam.domain.member.entity.member.Member;

@Getter
@Builder
public class LinkerProfileSummaryResponse {

  private String nickname;
  private String profileImageUrl;
  private String jobCategory;
  private String careerLevel;
  private String oneLineDescription;

  public static LinkerProfileSummaryResponse of(Linker linker, Member member) {
    return LinkerProfileSummaryResponse.builder()
        .nickname(linker.getNickname())
        .profileImageUrl(member.getProfileImageUrl())
        .jobCategory(linker.getJobCategory().getTitle())
        .careerLevel(linker.getCareerLevel().getTitle())
        .oneLineDescription(linker.getOneLineDescription())
        .build();
  }
}
