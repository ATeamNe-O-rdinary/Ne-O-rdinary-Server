package org.ateam.ateam.domain.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.member.entity.Member;
import org.ateam.ateam.domain.member.repository.MemberRepository;
import org.ateam.ateam.domain.member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public Page<Void> getMembersProfile(String categoryOfBusiness, Integer minSalary, Integer maxSalary){

        Page<Member> memberList = memberRepository.findByCategoryOfBusiness(categoryOfBusiness, minSalary, maxSalary);





        return null;


    }
}
