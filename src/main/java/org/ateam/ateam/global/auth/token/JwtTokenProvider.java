package org.ateam.ateam.global.auth.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ateam.ateam.global.security.JwtValidationType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String USER_ID = "userId";
    private static final Long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 60; // 1시간
    private static final Long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 14; // 14일

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    private SecretKey signingKey;

    @PostConstruct
    protected void init() {
        byte[] decodedKey = Base64.getUrlDecoder().decode(JWT_SECRET);
        this.signingKey = Keys.hmacShaKeyFor(decodedKey);
        log.debug("[JWT] Signing key initialized (Base64-decoded)");
    }

    /** AccessToken을 생성합니다. */
    public String generateAccessToken(Long userId) {
        return generateToken(userId, ACCESS_TOKEN_EXPIRE_TIME, "ACCESS");
    }

    /** RefreshToken을 생성합니다. */
    public String generateRefreshToken(Long userId) {
        return generateToken(userId, REFRESH_TOKEN_EXPIRE_TIME, "REFRESH");
    }

    /** JWT를 생성합니다. (JJWT 0.12.x 방식) */
    public String generateToken(Long userId, long expiryMillis, String tokenType) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiryMillis);

        String jwt =
                Jwts.builder()
                        .header()
                        .type("JWT")
                        .and()
                        .claim(USER_ID, userId)
                        .issuedAt(now)
                        .expiration(expiryDate)
                        .signWith(signingKey, Jwts.SIG.HS256)
                        .compact();

        log.info("[JWT] {} Token 생성 - userId={}, 만료시각: {}", tokenType, userId, expiryDate);
        return jwt;
    }

    private SecretKey getSigningKey() {
        return signingKey;
    }

    /** 토큰의 유효성을 검증합니다. */
    public JwtValidationType validateToken(String token) {
        try {
            final Claims claims = getTokenBody(token);
            return JwtValidationType.VALID_JWT;
        } catch (SignatureException ex) {
            return JwtValidationType.INVALID_JWT_SIGNATURE;
        } catch (MalformedJwtException ex) {
            return JwtValidationType.INVALID_JWT_TOKEN;
        } catch (ExpiredJwtException ex) {
            return JwtValidationType.EXPIRED_JWT_TOKEN;
        } catch (UnsupportedJwtException ex) {
            return JwtValidationType.UNSUPPORTED_JWT_TOKEN;
        } catch (IllegalArgumentException ex) {
            return JwtValidationType.EMPTY_JWT;
        }
    }

    /** JWT에서 userId를 추출합니다. */
    public Long parseUserId(String token) {
        if (!StringUtils.hasText(token)) {
            throw new IllegalArgumentException("JWT is null or empty");
        }

        Claims claims = getTokenBody(token);
        Object userIdObj = claims.get(USER_ID);

        if (userIdObj == null) {
            throw new IllegalArgumentException("Invalid JWT: missing userId");
        }

        try {
            return Long.parseLong(userIdObj.toString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid userId format in JWT");
        }
    }

    /** JWT Body를 추출합니다. (JJWT 0.12.x 방식) */
    private Claims getTokenBody(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /** 액세스 토큰 만료 시간을 초 단위로 반환합니다. */
    public Long getAccessTokenExpirySeconds() {
        return ACCESS_TOKEN_EXPIRE_TIME / 1000L;
    }
}