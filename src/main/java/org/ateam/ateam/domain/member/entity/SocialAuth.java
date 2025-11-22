package org.ateam.ateam.domain.member.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ateam.ateam.global.auth.enums.Provider;
import org.ateam.ateam.global.common.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
    name = "social_auth",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "provider"})})
public class SocialAuth extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long socialAuthId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private Member member;

  @Enumerated(EnumType.STRING)
  @Column(name = "provider", nullable = false, length = 20)
  private Provider provider;

  @Column(name = "provider_id", nullable = false)
  private String providerId;

  @Column(name = "access_token", length = 2048)
  private String accessToken;

  @Column(name = "refresh_token", length = 2048)
  private String refreshToken;

  @Column(name = "token_expiry")
  private LocalDateTime tokenExpiry;

  @Builder
  public SocialAuth(
      Member member,
      Provider provider,
      String providerId,
      String accessToken,
      String refreshToken,
      LocalDateTime tokenExpiry) {
    this.member = member;
    this.provider = provider;
    this.providerId = providerId;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.tokenExpiry = tokenExpiry;
  }

  /** Refresh Token 업데이트 */
  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  /** Access Token 업데이트 */
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  /** Member 설정 */
  public void setMember(Member member) {
    this.member = member;
  }

  /** Refresh Token과 만료시간 업데이트 */
  public void updateRefreshToken(String refreshToken, LocalDateTime tokenExpiry) {
    this.refreshToken = refreshToken;
    this.tokenExpiry = tokenExpiry;
  }

  /** Token 만료 여부 확인 */
  public boolean isTokenExpired() {
    if (tokenExpiry == null) {
      return true;
    }
    return LocalDateTime.now().isAfter(tokenExpiry);
  }
}
