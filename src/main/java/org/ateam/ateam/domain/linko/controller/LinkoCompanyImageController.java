package org.ateam.ateam.domain.linko.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.linko.controller.response.LinkoCompanyImageResponse;
import org.ateam.ateam.domain.linko.service.LinkoCompanyImageService;
import org.ateam.ateam.global.auth.context.UserContext;
import org.ateam.ateam.global.config.swagger.ApiErrorCodeExamples;
import org.ateam.ateam.global.dto.ResponseDto;
import org.ateam.ateam.global.error.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/linkos")
@Tag(name = "Linko Company Image", description = "회사 카드 이미지 관리 API")
public class LinkoCompanyImageController {

  private final LinkoCompanyImageService linkoCompanyImageService;
  private final UserContext userContext;

  @Operation(summary = "회사 카드 이미지 업로드")
  @PostMapping(value = "/company-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ApiErrorCodeExamples({ErrorCode.LINKO_NOT_FOUND})
  public ResponseDto<LinkoCompanyImageResponse> uploadCompanyImage(
      @RequestPart("file") MultipartFile file) {

    Long memberId = userContext.getCurrentUserId();

    LinkoCompanyImageResponse response =
        linkoCompanyImageService.uploadCompanyImage(memberId, file);

    return ResponseDto.of(
        HttpStatus.OK, "LINKO_COMPANY_IMAGE_UPLOAD_SUCCESS", "회사 카드 이미지 업로드 성공", response);
  }
}
