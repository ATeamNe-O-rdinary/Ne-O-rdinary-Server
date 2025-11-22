package org.ateam.ateam.domain.member.repository;

import java.util.List;
import java.util.Optional;
import org.ateam.ateam.domain.member.entity.SocialAuth;
import org.ateam.ateam.global.auth.enums.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SocialAuthRepository extends JpaRepository<SocialAuth, Long> {

  /** 회원 이메일과 Provider로 소셜 인증 정보 조회 */
  Optional<SocialAuth> findByMember_EmailAndProvider(String email, Provider provider);

  /** Provider ID와 Provider로 소셜 인증 정보 조회 (중복 가입 방지용) */
  Optional<SocialAuth> findByProviderIdAndProvider(String providerId, Provider provider);

  /** 회원 ID로 Refresh Token 조회 */
  @Query("SELECT sa.refreshToken FROM SocialAuth sa WHERE sa.member.id = :memberId")
  Optional<String> findRefreshTokenByUserId(@Param("memberId") Long memberId);

  /** Refresh Token 업데이트 */
  @Transactional
  @Modifying
  @Query("UPDATE SocialAuth sa SET sa.refreshToken = :refreshToken WHERE sa.member.id = :memberId")
  void updateRefreshToken(
      @Param("memberId") Long memberId, @Param("refreshToken") String refreshToken);

  /** 회원 ID와 Provider로 소셜 인증 정보 조회 */
  Optional<SocialAuth> findByMember_IdAndProvider(Long memberId, Provider provider);

  /** 회원의 모든 소셜 인증 정보 조회 */
  @Query("SELECT sa FROM SocialAuth sa WHERE sa.member.id = :memberId")
  List<SocialAuth> findAllByMemberId(@Param("memberId") Long memberId);

  /** 만료된 토큰을 가진 소셜 인증 정보 조회 */
  @Query("SELECT sa FROM SocialAuth sa WHERE sa.tokenExpiry < CURRENT_TIMESTAMP")
  List<SocialAuth> findExpiredTokens();
}
