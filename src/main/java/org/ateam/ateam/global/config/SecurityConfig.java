package org.ateam.ateam.global.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ateam.ateam.global.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        // CSRF 비활성화 (JWT 사용)
        .csrf(AbstractHttpConfigurer::disable)

        // CORS 설정
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))

        // 세션 사용 안 함 (JWT 사용)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // 요청 인증 설정
        .authorizeHttpRequests(
            auth ->
                auth
                    // 인증 없이 접근 가능한 URL
                    .requestMatchers(
                        "/api/v1/auth/**",
                        "/actuator/health",
                        "/health",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/h2-console/**",
                        "/favicon.ico",
                        "/error")
                    .permitAll()
                    // 나머지는 인증 필요
                    .anyRequest()
                    .authenticated())

        // H2 Console 프레임 옵션 비활성화
        .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))

        // JWT 필터 추가
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  /** CORS 설정 */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowedOriginPatterns(List.of("*")); // 모든 origin 허용 (개발용)
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);
    configuration.setExposedHeaders(List.of("Authorization"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}
