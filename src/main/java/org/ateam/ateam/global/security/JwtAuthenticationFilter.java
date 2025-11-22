package org.ateam.ateam.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ateam.ateam.global.auth.token.JwtTokenProvider;
import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.handler.ErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.objectMapper = new ObjectMapper();
    }

    // 인증 없이 허용되는 URL 패턴
    private static final List<String> NO_AUTH_URLS =
            List.of(
                    "/auth/**",
                    "/actuator/health",
                    "/health",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/h2-console/**",
                    "/favicon.ico",
                    "/error");

    /**
     * JWT 인증 필터 처리 메서드
     *
     * <p>Access Token을 추출하고, 토큰 유효성을 검증합니다. SecurityContext에 인증 정보를 등록합니다. 유효하지 않은 토큰의 경우 인증 실패 응답을
     * 반환합니다.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        log.debug("[JWT Filter] Request URI: {}", requestURI);

        // 인증이 필요 없는 URI 필터 통과
        if (isExcludedPath(requestURI)) {
            log.debug("[JWT Filter] 인증 제외 경로: {}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        // Authorization 헤더 검증
        if (header == null || !header.startsWith("Bearer ")) {
            log.warn("[JWT Filter] Authorization 헤더 없음 또는 형식 오류 - IP: {}", request.getRemoteAddr());
            sendUnauthorized(response, ErrorCode.JWT_NOT_FOUND);
            return;
        }

        // JWT 추출
        String token = header.substring(7);
        if (token.isBlank()) {
            log.warn("[JWT Filter] 토큰이 비어있음");
            sendUnauthorized(response, ErrorCode.JWT_NOT_FOUND);
            return;
        }

        try {
            // 토큰 유효성 검증
            JwtValidationType result = jwtTokenProvider.validateToken(token);

            if (result != JwtValidationType.VALID_JWT) {
                log.warn(
                        "[JWT Filter] 토큰 검증 실패 - type: {}, user-agent: {}",
                        result.name(),
                        request.getHeader("User-Agent"));
                sendUnauthorized(response, mapJwtValidationTypeToErrorCode(result));
                return;
            }

            // userId 추출
            Long userId = jwtTokenProvider.parseUserId(token);

            // SecurityContext에 인증 정보 등록
            UserAuthentication authentication = new UserAuthentication(userId, null, null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.debug("[JWT Filter] 인증 성공 - userId: {}", userId);

            filterChain.doFilter(request, response);

        } catch (Exception exception) {
            log.error("[JWT Filter] JWT 처리 중 오류: {}", exception.getMessage(), exception);
            sendUnauthorized(response, ErrorCode.AUTHENTICATION_FAILED);
        }
    }

    /**
     * URI가 인증 제외 경로인지 확인합니다.
     */
    private boolean isExcludedPath(String requestURI) {
        return NO_AUTH_URLS.stream().anyMatch(pattern -> pathMatcher.match(pattern, requestURI));
    }

    /**
     * JWT 검증 타입을 ErrorCode로 매핑합니다.
     */
    private ErrorCode mapJwtValidationTypeToErrorCode(JwtValidationType validationType) {
        return switch (validationType) {
            case EXPIRED_JWT_TOKEN -> ErrorCode.EXPIRED_JWT;
            case INVALID_JWT_SIGNATURE -> ErrorCode.SIGNATURE_INVALID_JWT;
            case UNSUPPORTED_JWT_TOKEN -> ErrorCode.UNSUPPORTED_JWT;
            case EMPTY_JWT -> ErrorCode.JWT_NOT_FOUND;
            default -> ErrorCode.AUTHENTICATION_FAILED;
        };
    }

    /**
     * 인증 실패 응답을 클라이언트에 전송합니다.
     */
    private void sendUnauthorized(HttpServletResponse response, ErrorCode errorCode)
            throws IOException {
        SecurityContextHolder.clearContext(); // 인증 정보 제거

        response.setStatus(errorCode.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);

        response.getWriter().write(jsonResponse);
        response.getWriter().flush();

        log.debug("[JWT Filter] 인증 실패 응답 전송 - code: {}", errorCode.getCode());
    }
}