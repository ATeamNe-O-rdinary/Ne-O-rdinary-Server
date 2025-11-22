package org.ateam.ateam.domain.member.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;
import org.ateam.ateam.domain.member.enums.Gender;
import org.ateam.ateam.domain.member.enums.Provider;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 3, nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "login_provider", nullable = false)
  @Enumerated(EnumType.STRING)
  private Provider loginProvider;

  @Column(name = "gender", nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Gender gender = Gender.NONE;

  @Column(name = "birth", nullable = false)
  private LocalDate birth;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "delete_at")
  private LocalDateTime deletedAt;

  @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
  private Spec spec;
}
