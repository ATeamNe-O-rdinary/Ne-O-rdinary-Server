package org.ateam.ateam.domain.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.member.converter.MemberConverter;
import org.ateam.ateam.domain.member.dto.req.MemberReqDTO;
import org.ateam.ateam.domain.member.dto.res.MemberResDTO;
import org.ateam.ateam.domain.member.entity.Member;
import org.ateam.ateam.domain.member.entity.Spec;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;
import org.ateam.ateam.domain.member.repository.MemberRepository;
import org.ateam.ateam.domain.member.service.MemberService;
import org.ateam.ateam.global.dto.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public PagedResponse<MemberResDTO.ProfileListDTO> getProfileList(
            MemberReqDTO.ProfileListDTO dto, Pageable pageable) {

        Spec spec = MemberConverter.toSpecEntity(dto);

        CategoryOfBusiness category = spec.getCategoryOfBusiness();
        Integer minSalary = spec.getMinSalary();
        Integer maxSalary = spec.getMaxSalary();

        Pageable unsortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        Page<Member> memberList =
                memberRepository.findByCategoryOfBusiness(category, minSalary, maxSalary, unsortedPageable);

        Page<MemberResDTO.ProfileListDTO> pageList = memberList.map(MemberConverter::toProfileListDTO);

        return PagedResponse.pagedFrom(pageList);
    }
    @Override
    public CategoryOfBusiness getCategoryOfBusiness(Long memberId, String linkTingRole) {
        CategoryOfBusiness category = null;
        if("linker".equals(linkTingRole)){

        }
        else if("linko".equals(linkTingRole)){

        }
        else{

        }
        return category;

    }
}


