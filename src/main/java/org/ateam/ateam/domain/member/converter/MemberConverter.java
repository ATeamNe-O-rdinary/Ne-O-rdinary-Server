package org.ateam.ateam.domain.member.converter;

import org.ateam.ateam.domain.member.dto.res.MemberResDTO;
import org.ateam.ateam.domain.member.entity.Member;

public class MemberConverter {

    public static MemberResDTO.ProfileListDTO toProfileListDTO(Member member) {
        return MemberResDTO.ProfileListDTO.builder()
                .memberId(member.getId())
                .username(member.getUsername())
                .build();
    }
}
