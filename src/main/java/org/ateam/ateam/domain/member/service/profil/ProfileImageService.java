package org.ateam.ateam.domain.member.service.profil;

import org.springframework.web.multipart.MultipartFile;

public interface ProfileImageService {

  String profileUpload(Long memberId, MultipartFile file);

  void profileDelete(Long memberId);

  String profileGet(Long memberId);
}
