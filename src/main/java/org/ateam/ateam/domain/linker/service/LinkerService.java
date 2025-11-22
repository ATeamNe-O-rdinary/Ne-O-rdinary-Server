package org.ateam.ateam.domain.linker.service;

import org.ateam.ateam.domain.linker.controller.response.LinkerResponse;
import org.ateam.ateam.domain.linker.model.request.LinkerCreateRequest;
import org.ateam.ateam.domain.linker.model.request.LinkerUpdateRequest;
import org.ateam.ateam.global.dto.PagedResponse;
import org.springframework.data.domain.Pageable;

public interface LinkerService {

	LinkerResponse linkerCreate(Long memberId, LinkerCreateRequest request);

	LinkerResponse getById(Long linkerId);

	PagedResponse<LinkerResponse> getPage(Pageable pageable);

	LinkerResponse linkerUpdate(Long memberId, Long linkerId, LinkerUpdateRequest request);

	void linkerDelete(Long memberId, Long linkerId);
}
