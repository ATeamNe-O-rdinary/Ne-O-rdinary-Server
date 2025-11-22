package org.ateam.ateam.global.auth.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthInfoDTO {
  private String providerId;
  private Long userId;
  private String email;
  private String username;
  private LocalDateTime tokenExpiry;
}
