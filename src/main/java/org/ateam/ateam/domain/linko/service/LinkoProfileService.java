package org.ateam.ateam.domain.linko.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ateam.ateam.domain.linko.controller.response.LinkoProfileResDTO;
import org.ateam.ateam.domain.linko.exception.LinkoProfileNotFoundException;
import org.ateam.ateam.domain.linko.model.Linko;
import org.ateam.ateam.domain.linko.model.request.LinkoProfileReqDTO;
import org.ateam.ateam.domain.linko.repository.LinkoRepository;
import org.ateam.ateam.domain.member.entity.Member;
import org.ateam.ateam.domain.linko.exception.ProfileAlreadyExistsException;
import org.ateam.ateam.domain.member.repository.MemberRepository;
import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LinkoProfileService {

    private final LinkoRepository repository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createProfile(Long memberId, LinkoProfileReqDTO request) {
        if (repository.existsByMember_Id(memberId)) {
            throw new ProfileAlreadyExistsException();
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        Linko linko = request.toEntity(member);
        repository.save(linko);

        log.info("[Linko] 프로필 등록 완료 - memberId={}", memberId);
    }

    public LinkoProfileResDTO getProfile(Long memberId) {
        Linko linko = repository.findByMember_Id(memberId)
                .orElseThrow(LinkoProfileNotFoundException::new);

        return LinkoProfileResDTO.from(linko);
    }

    public LinkoProfileResDTO getProfileById(Long linkoId) {
        Linko linko = repository.findById(linkoId)
                .orElseThrow(LinkoProfileNotFoundException::new);

        return LinkoProfileResDTO.from(linko);
    }

    @Transactional
    public void updateProfile(Long linkoId, Long memberId, LinkoProfileReqDTO request) {
        Linko linko = repository.findById(linkoId)
                .orElseThrow(LinkoProfileNotFoundException::new);

        // 본인 확인
        if (!linko.getMember().getId().equals(memberId)) {
            throw new BusinessException(ErrorCode.HANDLE_ACCESS_DENIED);
        }

        linko.update(request);

        log.info("[Linko] 프로필 수정 완료 - linkoId={}, memberId={}", linkoId, memberId);
    }

    @Transactional
    public void deleteProfile(Long linkoId, Long memberId) {
        Linko linko = repository.findById(linkoId)
                .orElseThrow(LinkoProfileNotFoundException::new);

        // 본인 확인
        if (!linko.getMember().getId().equals(memberId)) {
            throw new BusinessException(ErrorCode.HANDLE_ACCESS_DENIED);
        }

        repository.delete(linko);

        log.info("[Linko] 프로필 삭제 완료 - linkoId={}, memberId={}", linkoId, memberId);
    }
}