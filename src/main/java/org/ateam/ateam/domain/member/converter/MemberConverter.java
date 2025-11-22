package org.ateam.ateam.domain.member.converter;

import org.ateam.ateam.domain.member.dto.req.MemberReqDTO;
import org.ateam.ateam.domain.member.dto.res.MemberResDTO;
import org.ateam.ateam.domain.member.entity.Member;
import org.ateam.ateam.domain.member.entity.Spec;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;

public class MemberConverter {

  public static MemberResDTO.ProfileListDTO toProfileListDTO(Member member) {
    return MemberResDTO.ProfileListDTO.builder()
        .memberId(member.getId())
        .username(member.getUsername())
            .profileImage(member.getProfileImage())
        .build();
  }

  public static Spec toSpecEntity(MemberReqDTO.ProfileListDTO dto) {
    return Spec.builder()
        .categoryOfBusiness(CategoryOfBusiness.from(dto.categoryOfBusiness()))
        .minSalary(dto.minSalary())
        .maxSalary(dto.maxSalary())
        .build();
  }
}
