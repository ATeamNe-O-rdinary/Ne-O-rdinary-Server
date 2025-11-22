package org.ateam.ateam.domain.linko.service;

import org.ateam.ateam.domain.linko.controller.response.LinkoCompanyImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface LinkoCompanyImageService {

  LinkoCompanyImageResponse uploadCompanyImage(Long memberId, MultipartFile file);
}
