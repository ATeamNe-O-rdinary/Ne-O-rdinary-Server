package org.ateam.ateam.global.s3;

import lombok.RequiredArgsConstructor;
import org.ateam.ateam.global.config.swagger.ApiErrorCodeExamples;
import org.ateam.ateam.global.dto.ResponseDto;
import org.ateam.ateam.global.error.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class S3Controller {

  private final S3Uploader s3Uploader;

  @Operation(summary = "파일 업로드")
  @ApiErrorCodeExamples({
      ErrorCode.S3_UPLOAD_FAILED,
      ErrorCode.S3_INVALID_URL
  })
  @PostMapping
  public ResponseDto<S3UploadResponse> upload(
      @RequestPart("file") MultipartFile file,
      @RequestParam(defaultValue = "default") String dirName
  ) {
    String url = s3Uploader.upload(file, dirName);
    return ResponseDto.of(
        HttpStatus.OK,
        "S3_UPLOAD_SUCCESS",
        "파일 업로드 성공",
        new S3UploadResponse(url)
    );
  }

  @Operation(summary = "파일 삭제")
  @ApiErrorCodeExamples({
      ErrorCode.S3_DELETE_FAILED,
      ErrorCode.S3_INVALID_URL
  })
  @DeleteMapping
  public ResponseDto<Void> delete(@RequestParam String url) {
    s3Uploader.delete(url);
    return ResponseDto.of(
        HttpStatus.NO_CONTENT,
        "S3_DELETE_SUCCESS",
        "파일 삭제 성공"
    );
  }
}
