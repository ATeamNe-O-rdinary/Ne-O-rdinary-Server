package org.ateam.ateam.domain.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.member.entity.Member;
import org.ateam.ateam.domain.member.service.ProfileImageService;
import org.ateam.ateam.domain.member.validator.ProfileImageValidator;
import org.ateam.ateam.global.s3.service.S3Uploader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileImageServiceImpl implements ProfileImageService {

  private final S3Uploader s3Uploader;
  private final ProfileImageValidator profileImageValidator;
  private final ProfileImageValidator validator;
  private static final String PROFILE_DIR = "profile";

  @Override
  public String profileUpload(Long memberId, MultipartFile file) {
    Member member = validator.getMemberOrThrow(memberId);
    profileImageValidator.validateAlreadyExists(member);
    String url = s3Uploader.upload(file, PROFILE_DIR);
    member.updateProfileImage(url);
    return url;
  }

  @Override
  public void profileDelete(Long memberId) {
    Member member = validator.getMemberOrThrow(memberId);
    profileImageValidator.validateExists(member);
    s3Uploader.delete(member.getProfileImageUrl());
    member.updateProfileImage(null);
  }

  @Override
  @Transactional(readOnly = true)
  public String profileGet(Long memberId) {
    Member member = validator.getMemberOrThrow(memberId);
    profileImageValidator.validateExists(member);
    return member.getProfileImageUrl();
  }
}
