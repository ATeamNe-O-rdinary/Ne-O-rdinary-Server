package org.ateam.ateam.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ateam.ateam.domain.member.dto.req.LinkoProfileReqDTO;
import org.ateam.ateam.domain.member.dto.res.LinkoProfileResDTO;
import org.ateam.ateam.domain.member.entity.Linko;
import org.ateam.ateam.domain.member.exception.LinkoProfileNotFoundException;
import org.ateam.ateam.domain.member.exception.ProfileAlreadyExistsException;
import org.ateam.ateam.domain.member.repository.LinkoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LinkoProfileService {

    private final LinkoRepository repository;

    @Transactional
    public void createProfile(Long memberId, LinkoProfileReqDTO request) {
        if (repository.existsByMemberId(memberId)) {
            throw new ProfileAlreadyExistsException();
        }

        Linko linko = request.toEntity(memberId);
        repository.save(linko);

        log.info("[Linko Profile] 프로필 등록 완료 - memberId={}", memberId);
    }

    public LinkoProfileResDTO getProfile(Long memberId) {
        Linko linko = repository.findByMemberId(memberId)
                .orElseThrow(LinkoProfileNotFoundException::new);

        return LinkoProfileResDTO.from(linko);
    }

    @Transactional
    public void updateProfile(Long memberId, LinkoProfileReqDTO request) {
        Linko linko = repository.findByMemberId(memberId)
                .orElseThrow(LinkoProfileNotFoundException::new);

        linko.update(request);

        log.info("[Linko Profile] 프로필 수정 완료 - memberId={}", memberId);
    }

    @Transactional
    public void deleteProfile(Long memberId) {
        Linko linko = repository.findByMemberId(memberId)
                .orElseThrow(LinkoProfileNotFoundException::new);

        repository.delete(linko);

        log.info("[Linko Profile] 프로필 삭제 완료 - memberId={}", memberId);
    }
}