package org.ateam.ateam.domain.linko.service.impl;

import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.linko.controller.response.LinkoCompanyImageResponse;
import org.ateam.ateam.domain.linko.model.Linko;
import org.ateam.ateam.domain.linko.service.LinkoCompanyImageService;
import org.ateam.ateam.domain.linko.validator.LinkoValidator;
import org.ateam.ateam.global.s3.S3Uploader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class LinkoCompanyImageServiceImpl implements LinkoCompanyImageService {

  private final LinkoValidator linkoValidator;
  private final S3Uploader s3Uploader;

  @Override
  public LinkoCompanyImageResponse uploadCompanyImage(Long linkoId, MultipartFile file) {

    Linko linko = linkoValidator.getByIdOrThrow(linkoId);

    if (linko.getCompanyImageUrl() != null) {
      s3Uploader.delete(linko.getCompanyImageUrl());
    }
    String imageUrl = s3Uploader.upload(file, "linko/company");
    linko.updateCompanyImage(imageUrl);

    return LinkoCompanyImageResponse.from(imageUrl);
  }
}
