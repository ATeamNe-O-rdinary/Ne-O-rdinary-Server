package org.ateam.ateam.domain.member.service;

import org.ateam.ateam.domain.member.dto.req.MemberReqDTO;
import org.ateam.ateam.domain.member.dto.res.MemberResDTO;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;
import org.ateam.ateam.global.dto.PagedResponse;
import org.springframework.data.domain.Pageable;

public interface MemberService {

  PagedResponse<MemberResDTO.ProfileListDTO> getProfileList(
      MemberReqDTO.ProfileListDTO dto, Pageable pageable);
    CategoryOfBusiness getCategoryOfBusiness(Long memberId, String linkTingRole);
}
