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
  private String providerId; // OAuth 제공자의 고유 ID
  private Long userId;
  private String email;
  private String username;
  private LocalDateTime tokenExpiry; // social access token 의 만료일
}
