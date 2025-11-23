package org.ateam.ateam.domain.member.converter;

import org.ateam.ateam.domain.linker.model.entity.Linker;
import org.ateam.ateam.domain.linko.model.Linko;
import org.ateam.ateam.domain.member.dto.response.MemberResponse;
import org.ateam.ateam.domain.member.entity.member.Member;

public class MemberConverter {

  public static MemberResponse.ProfileListDTO toProfileListDTO(Member member) {
    return MemberResponse.ProfileListDTO.builder()
        .memberId(member.getId())
        .username(member.getUsername())
        .profileImage(member.getProfileImage())
        .build();
  }

  public static MemberResponse.LinkerProfileDTO toLinkerProfileDTO(Linker linker) {
    String profileImageUrl =
        (linker.getMember() != null) ? linker.getMember().getProfileImage() : null;

    return MemberResponse.LinkerProfileDTO.builder()
        .nickname(linker.getNickname())
        .jobCategory(linker.getJobCategory())
        .careerLevel(linker.getCareerLevel())
        .oneLineDescription(linker.getOneLineDescription())
        .workTimeType(linker.getWorkTimeType())
        .rateUnit(linker.getRateUnit())
        .rateAmount(linker.getRateAmount())
        .collaborationType(linker.getCollaborationType())
        .region(linker.getRegion())
        .techStacks(linker.getTechStacks()) // Set<TechStack> 그대로 전달
        .profileImage(profileImageUrl)
        .build();
  }

  public static MemberResponse.LinkoProfileDTO toLinkoProfileDTO(Linko linko) {
    String profileImageUrl =
        (linko.getMember() != null) ? linko.getMember().getProfileImage() : null;

    // deadline이 엔티티에서 LocalDateTime일 경우 String 변환 로직이 필요할 수 있음
    // 여기서는 String으로 가정하고 바로 매핑합니다.

    return MemberResponse.LinkoProfileDTO.builder()
        .companyName(linko.getCompanyName())
        .companyType(linko.getCompanyType())
        .mainCategory(linko.getMainCategory())
        .categoryOfBusiness(linko.getCategoryOfBusiness())
        .projectIntro(linko.getProjectIntro())
        .expectedDuration(linko.getExpectedDuration())
        .rateUnit(linko.getRateUnit())
        .rateAmount(linko.getRateAmount())
        .collaborationType(linko.getCollaborationType())
        .region(linko.getRegion())
        .deadline(linko.getDeadline())
        .techStacks(linko.getTechStacks())
        .profileImage(profileImageUrl)
        .build();
  }
}
