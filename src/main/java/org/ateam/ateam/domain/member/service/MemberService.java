package org.ateam.ateam.domain.member.service;

import org.springframework.data.domain.Page;

public interface MemberService {


    Page<Void> getMembersProfile();
}
