package org.ateam.ateam.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.member.service.ProfileImageService;
import org.ateam.ateam.global.auth.context.UserContext;
import org.ateam.ateam.global.config.swagger.ApiErrorCodeExamples;
import org.ateam.ateam.global.dto.ResponseDto;
import org.ateam.ateam.global.error.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/profile-image")
@Tag(name = "ProfileImage", description = "Profile Image 관리 API")
public class ProfileImageController {

  private final ProfileImageService profileImageService;
  private final UserContext userContext;

  @Operation(summary = "프로필 이미지 업로드")
  @ApiErrorCodeExamples({
    ErrorCode.USER_NOT_FOUND,
    ErrorCode.PROFILE_ALREADY_EXISTS,
    ErrorCode.S3_UPLOAD_FAILED
  })
  @PostMapping(consumes = "multipart/form-data")
  public ResponseDto<ProfileImageResponse> upload(@RequestPart("file") MultipartFile file) {
    Long memberId = userContext.getCurrentUserId();
    String url = profileImageService.profileUpload(memberId, file);

    return ResponseDto.of(
        HttpStatus.CREATED,
        "PROFILE_UPLOAD_SUCCESS",
        "프로필 이미지 업로드 성공",
        new ProfileImageResponse(url));
  }

  @Operation(summary = "프로필 이미지 조회")
  @ApiErrorCodeExamples({ErrorCode.USER_NOT_FOUND, ErrorCode.PROFILE_NOT_FOUND})
  @GetMapping
  public ResponseDto<ProfileImageResponse> get() {
    Long memberId = userContext.getCurrentUserId();
    String url = profileImageService.profileGet(memberId);

    return ResponseDto.of(
        HttpStatus.OK, "PROFILE_GET_SUCCESS", "프로필 이미지 조회 성공", new ProfileImageResponse(url));
  }

  @Operation(summary = "프로필 이미지 삭제")
  @ApiErrorCodeExamples({
    ErrorCode.USER_NOT_FOUND,
    ErrorCode.PROFILE_NOT_FOUND,
    ErrorCode.S3_DELETE_FAILED
  })
  @DeleteMapping
  public ResponseDto<Void> delete() {
    Long memberId = userContext.getCurrentUserId();
    profileImageService.profileDelete(memberId);

    return ResponseDto.of(HttpStatus.NO_CONTENT, "PROFILE_DELETE_SUCCESS", "프로필 이미지 삭제 성공");
  }

  public record ProfileImageResponse(String url) {}
}
