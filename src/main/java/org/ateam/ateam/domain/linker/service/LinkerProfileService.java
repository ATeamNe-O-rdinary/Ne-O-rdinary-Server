package org.ateam.ateam.domain.linker.service;

import org.ateam.ateam.domain.linker.controller.response.LinkerProfileSummaryResponse;

public interface LinkerProfileService {

	LinkerProfileSummaryResponse getMyProfileSummary(Long memberId);
}
