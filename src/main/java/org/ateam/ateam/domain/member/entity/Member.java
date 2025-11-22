package org.ateam.ateam.domain.member.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;
import org.ateam.ateam.global.common.BaseEntity;
import org.ateam.ateam.domain.member.enums.Gender;
import org.ateam.ateam.global.auth.enums.Provider;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Member extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 50, nullable = false)
  private String username;

  @Column(name = "email", unique = true)
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(name = "login_provider", nullable = false, length = 20)
  private Provider loginProvider;

  @Column(name = "profile_image_url", length = 500)
  private String profileImageUrl;

  @Column(name = "gender", nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Gender gender = Gender.NONE;

  @Column(name = "birth")
  private LocalDate birth;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "delete_at")
  private LocalDateTime deletedAt;

  private String profileImage;

  private Long view;

  @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
  private Spec spec;

  /** Username 변경 */
  public void setUsername(String username) {
    this.username = username;
  }

  /** 회원 탈퇴 처리 */
  public void delete() {
    this.deletedAt = LocalDateTime.now();
  }

  /** 회원 재활성화 처리 */
  public void reactivate() {
    this.deletedAt = null;
  }

  /** 탈퇴 여부 확인 */
  public boolean isDeleted() {
    return this.deletedAt != null;
  }

  /** 활성 회원 여부 확인 */
  public boolean isActive() {
    return this.deletedAt == null;
  }

  /** 프로필 이미지 업데이트 */
  public void updateProfileImage(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }
}
