// package org.ateam.ateam.global.jwt;
//
// import java.util.Date;
// import java.util.UUID;
//
// import javax.crypto.SecretKey;
//
// import org.ateam.ateam.global.jwt.dto.TokenResponse;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Component;
//
// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.ExpiredJwtException;
// import io.jsonwebtoken.Jws;
// import io.jsonwebtoken.JwtException;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.io.Decoders;
// import io.jsonwebtoken.security.Keys;
// import jakarta.annotation.PostConstruct;
// import jakarta.servlet.http.Cookie;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @Slf4j
// @RequiredArgsConstructor
// @Component
// public class JwtProvider {
//
//   private final UserRepository userRepository;
//
//   @Value("${jwt.secret}")
//   private String secretKeyString;
//
//   private SecretKey secretKey;
//
//   private static final String BEARER_PREFIX = "Bearer ";
//   private static final String CLAIM_EMAIL = "email";
//   private static final String TOKEN_SUBJECT = "NewServer";
//
//   private static final long ACCESS_TOKEN_VALIDITY_MS = 30 * 60 * 1000L;
//   private static final long REFRESH_TOKEN_VALIDITY_MS = 7L * 24 * 60 * 60 * 1000L;
//
//   @PostConstruct
//   public void init() {
//     byte[] keyBytes = Decoders.BASE64.decode(secretKeyString);
//     this.secretKey = Keys.hmacShaKeyFor(keyBytes);
//   }
//
//   public String createAccessToken(String email) {
//     Date now = new Date();
//     return Jwts.builder()
//         .setSubject(TOKEN_SUBJECT)
//         .claim(CLAIM_EMAIL, email)
//         .setId(UUID.randomUUID().toString())
//         .setIssuedAt(now)
//         .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALIDITY_MS))
//         .signWith(secretKey, SignatureAlgorithm.HS256)
//         .compact();
//   }
//
//   public String createRefreshToken(String email) {
//     Date now = new Date();
//     return Jwts.builder()
//         .setSubject(TOKEN_SUBJECT)
//         .claim(CLAIM_EMAIL, email)
//         .setId(UUID.randomUUID().toString())
//         .setIssuedAt(now)
//         .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALIDITY_MS))
//         .signWith(secretKey, SignatureAlgorithm.HS256)
//         .compact();
//   }
//
//   public String resolveAccessToken(HttpServletRequest req) {
//     String token = resolveToken(req.getHeader("ACCESS"));
//     if (token != null) {
//       return token;
//     }
//     return resolveToken(req.getHeader("Authorization"));
//   }
//
//   public String resolveRefreshToken(HttpServletRequest req) {
//     if (req.getCookies() == null) {
//       return null;
//     }
//     for (Cookie c : req.getCookies()) {
//       if ("refreshToken".equals(c.getName())) {
//         return resolveToken(c.getValue());
//       }
//     }
//     return null;
//   }
//
//   public TokenResponse createTokens(String email) {
//     String accessToken = createAccessToken(email);
//     String refreshToken = createRefreshToken(email);
//     return new TokenResponse(accessToken, refreshToken);
//   }
//
//   public TokenResponse issueTokensAndAddHeaders(HttpServletResponse res, String email) {
//     TokenResponse tokens = createTokens(email);
//     addTokenHeaders(res, tokens);
//     return tokens;
//   }
//
//   private String resolveToken(String header) {
//     if (header != null && header.startsWith(BEARER_PREFIX)) {
//       return header.substring(BEARER_PREFIX.length());
//     }
//     return null;
//   }
//
//   public boolean validateAccessToken(String token) {
//     return validateToken(token, "Access");
//   }
//
//   private boolean validateToken(String token, String type) {
//     try {
//       Jws<Claims> claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
//       return !claims.getBody().getExpiration().before(new Date());
//     } catch (ExpiredJwtException e) {
//       log.info("{} Token 만료: {}", type, e.getMessage());
//     } catch (JwtException | IllegalArgumentException e) {
//       log.warn("{} Token 검증 실패: {}", type, e.getMessage());
//     }
//     return false;
//   }
//
//   public String getEmail(String token) {
//     Claims body = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getBody();
//     return body.get(CLAIM_EMAIL, String.class);
//   }
//
//   public Authentication getAuthentication(String token) {
//     String email = getEmail(token);
//     User user =
//         userRepository
//             .findByEmail(email)
//             .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
//
//     UserDetails userDetails = new UserDetailsImpl(user);
//     return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//   }
//
//   public void addTokenHeaders(HttpServletResponse res, TokenResponse tokens) {
//     String bearer = BEARER_PREFIX + tokens.getAccessToken();
//     res.setHeader("Authorization", bearer);
//   }
// }
