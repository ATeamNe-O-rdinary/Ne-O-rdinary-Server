package org.ateam.ateam.global.s3;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ateam.ateam.global.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "S3 파일", description = "S3 파일 업로드 / 삭제 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class S3Controller {

  private final S3Uploader s3Uploader;

  @Operation(summary = "파일 업로드", description = "S3에 파일을 업로드합니다.")
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ResponseDto<S3UploadResponse>> upload(
      @RequestPart("file") MultipartFile file,
      @RequestParam(defaultValue = "default") String dirName) {
    String url = s3Uploader.upload(file, dirName);
    S3UploadResponse response = new S3UploadResponse(url);

    return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, null, "파일 업로드 성공", response));
  }

  @Operation(summary = "파일 삭제", description = "S3에 업로드된 파일을 삭제합니다.")
  @DeleteMapping
  public ResponseEntity<ResponseDto<Void>> delete(@RequestParam String url) {
    s3Uploader.delete(url);

    return ResponseEntity.ok(ResponseDto.<Void>of(HttpStatus.OK, null, "파일 삭제 성공"));
  }
}
