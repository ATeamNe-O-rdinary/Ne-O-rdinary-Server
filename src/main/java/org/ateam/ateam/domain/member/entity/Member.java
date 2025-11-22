package org.ateam.ateam.domain.member.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;
import org.ateam.ateam.domain.member.enums.Gender;
import org.ateam.ateam.global.auth.enums.Provider;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 50, nullable = false)
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "email", unique = true) // nullable 제거 (이메일 선택사항)
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(name = "provider", nullable = false, length = 20)
  private Provider loginProvider;

  @Column(name = "gender", length = 10)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Gender gender = Gender.NONE;

  @Column(name = "birth")
  private LocalDate birth;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "profile_image_url", length = 500)
  private String profileImageUrl;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  // Spec 엔티티와의 관계는 Spec 쪽에서만 관리 (단방향)
  // @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
  // private Spec spec;

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
