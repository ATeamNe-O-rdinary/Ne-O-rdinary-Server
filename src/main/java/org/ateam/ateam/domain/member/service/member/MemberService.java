package org.ateam.ateam.domain.member.service.member;

import java.util.List;
import org.ateam.ateam.domain.member.dto.request.MemberRequest;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;
import org.ateam.ateam.global.dto.PagedResponse;
import org.springframework.data.domain.Pageable;

public interface MemberService {

  PagedResponse<?> getProfileList(MemberRequest.ProfileListDTO dto, Pageable pageable);

  CategoryOfBusiness getCategoryOfBusiness(Long memberId, String linkTingRole);

  List<CategoryOfBusiness> getCategoryOfBusinessList(CategoryOfBusiness categoryOfBusiness);
}
