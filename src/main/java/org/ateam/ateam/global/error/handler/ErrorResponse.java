package org.ateam.ateam.global.error.handler;

import org.ateam.ateam.global.error.ErrorCode;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "공통 에러 응답")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

  @Schema(description = "HTTP 상태 코드", example = "400")
  private int status;

  @Schema(description = "비즈니스 에러 코드", example = "U001")
  private String code;

  @Schema(description = "추가 데이터")
  private Object data;

  @Schema(description = "에러 메시지", example = "잘못된 요청입니다.")
  private String message;

  private ErrorResponse(final ErrorCode code, Object data) {
    this.status = code.getStatus();
    this.code = code.getCode();
    this.data = data;
    this.message = code.getMessage();
  }

  private ErrorResponse(final ErrorCode code) {
    this.status = code.getStatus();
    this.code = code.getCode();
    this.data = null;
    this.message = code.getMessage();
  }

  public static ErrorResponse of(final ErrorCode code) {
    return new ErrorResponse(code);
  }

  public static ErrorResponse of(final ErrorCode code, Object data) {
    return new ErrorResponse(code, data);
  }

  // 타입 에러도 data 없이 처리
  public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
    return new ErrorResponse(ErrorCode.INVALID_TYPE_VALUE);
  }
}
