// package org.ateam.ateam.global.jwt;
//
// import java.io.IOException;
// import java.util.List;
//
// import org.pro.newserver.global.error.ErrorCode;
// import org.pro.newserver.global.error.exception.JwtAuthenticationException;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContext;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;
//
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.RequiredArgsConstructor;
//
// @Component
// @RequiredArgsConstructor
// public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//   private final JwtProvider jwtProvider;
//
//   private static final List<String> EXCLUDE_URLS =
//       List.of(
//           // Swagger
//           "/swagger-ui",
//           "/swagger-ui/",
//           "/swagger-ui.html",
//           "/v3/api-docs",
//           "/v3/api-docs/",
//           "/v3/api-docs/",
//           "/api-docs",
//           "/api-docs/",
//           // 인증/회원가입
//           "/api/auth",
//           "/api/auth/",
//           "/api/auth/login",
//           "/api/auth/signup",
//           "/api/auth/reissue",
//           "/api/users",
//           "/api/users/",
//           "/api/users/signup",
//           // 헬스 체크
//           "/health");
//
//   @Override
//   protected boolean shouldNotFilter(HttpServletRequest request) {
//     String path = request.getRequestURI();
//     return EXCLUDE_URLS.stream().anyMatch(path::startsWith);
//   }
//
//   @Override
//   protected void doFilterInternal(
//       HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//       throws ServletException, IOException {
//
//     String token = jwtProvider.resolveAccessToken(request);
//
//     if (token == null) {
//       filterChain.doFilter(request, response);
//       return;
//     }
//
//     if (!jwtProvider.validateAccessToken(token)) {
//       throw new JwtAuthenticationException(ErrorCode.AUTHENTICATION_FAILED);
//     }
//
//     Authentication authentication = jwtProvider.getAuthentication(token);
//     SecurityContext context = SecurityContextHolder.createEmptyContext();
//     context.setAuthentication(authentication);
//     SecurityContextHolder.setContext(context);
//
//     filterChain.doFilter(request, response);
//   }
// }
