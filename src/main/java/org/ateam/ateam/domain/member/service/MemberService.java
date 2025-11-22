package org.ateam.ateam.domain.member.service;

import org.ateam.ateam.domain.member.dto.res.MemberResDTO;
import org.ateam.ateam.global.dto.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {


    

    PagedResponse<MemberResDTO.ProfileListDTO> getProfileList(String categoryOfBusiness, Integer minSalary, Integer maxSalary, Pageable pageable);
}
