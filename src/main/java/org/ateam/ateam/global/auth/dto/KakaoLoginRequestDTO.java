package org.ateam.ateam.global.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "카카오 로그인 요청 DTO")
public record KakaoLoginRequestDTO(
        @NotBlank(message = "accessToken은 필수입니다.")
        @Schema(description = "카카오 서버에서 발급받은 Access Token", example = "LSp3Z06wgUXXKjQDIPsKRNFgRmShI2ivAAAAAQoXFmIAAAGarYbw5LZbzBbpXusm")
        String accessToken,
        @NotBlank(message = "refreshToken은 필수입니다.")
        @Schema(description = "카카오 서버에서 발급받은 Refresh Token", example = "sJ2ef0AFgqSUePErENlIlmzwre_Z2sAaAAAAAgoXFmIAAAGarYbw3bZbzBbpXusm")
        String refreshToken) {
}
