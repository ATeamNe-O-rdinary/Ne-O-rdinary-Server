package org.ateam.ateam.domain.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.member.converter.MemberConverter;
import org.ateam.ateam.domain.member.dto.res.MemberResDTO;
import org.ateam.ateam.domain.member.entity.Member;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;
import org.ateam.ateam.domain.member.repository.MemberRepository;
import org.ateam.ateam.domain.member.service.MemberService;
import org.ateam.ateam.global.dto.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public PagedResponse<MemberResDTO.ProfileListDTO> getProfileList(String categoryOfBusiness, Integer minSalary, Integer maxSalary, Pageable pageable){

        CategoryOfBusiness categoryEnum = CategoryOfBusiness.from(categoryOfBusiness);

        Page<Member> memberList = memberRepository.findByCategoryOfBusiness(categoryEnum, minSalary, maxSalary, pageable);

        Page<MemberResDTO.ProfileListDTO> pageList = memberList.map(MemberConverter::toProfileListDTO);

        return PagedResponse.pagedFrom(pageList);

    }
}
