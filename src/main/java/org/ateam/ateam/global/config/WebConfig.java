package org.ateam.ateam.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/**")
			.allowedOrigins(
				"http://localhost:3000",
				"http://localhost:5173"
			)
			.allowedMethods("*")
			.allowedHeaders("*")
			.allowCredentials(true);
	}

    @Bean("kakaoWebClient")
    public WebClient kakaoWebClient() {
        return WebClient.builder()
                .baseUrl("https://kapi.kakao.com") // 사용자 정보 조회
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean("kakaoTokenWebClient")
    public WebClient kakaoTokenWebClient() {
        return WebClient.builder()
                .baseUrl("https://kauth.kakao.com") // 토큰 갱신용
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
    }
}
