package org.ateam.ateam.global.error;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
  // Common
  INVALID_INPUT_VALUE(400, "C001", "잘못된 입력 값입니다."),
  METHOD_NOT_ALLOWED(405, "C002", "허용되지 않은 메서드입니다."),
  ENTITY_NOT_FOUND(400, "C003", "엔티티를 찾을 수 없습니다."),
  INTERNAL_SERVER_ERROR(500, "C004", "서버 내부 오류가 발생했습니다."),
  INVALID_TYPE_VALUE(400, "C005", "유효하지 않은 타입 값입니다."),
  HANDLE_ACCESS_DENIED(403, "C006", "접근이 거부되었습니다."),

  // JWT
  EXPIRED_JWT(403, "J001", "만료된 JWT 토큰입니다."),
  UNSUPPORTED_JWT(403, "J002", "지원되지 않는 JWT 토큰입니다."),
  SIGNATURE_INVALID_JWT(403, "J003", "사용중인 시그니처키입니다."),
  JWT_NOT_FOUND(403, "J004", "JWT 토큰을 찾을 수 없습니다."),
  AUTHENTICATION_FAILED(403, "J005", "인증에 실패했습니다."),

  // User
  USER_NOT_FOUND(404, "U001", "사용자를 찾을 수 없습니다."),
  USER_ALREADY_REGISTERED(409, "U002", "이미 가입된 사용자입니다."),
  EMAIL_ALREADY_EXISTS(409, "U003", "이미 사용 중인 이메일입니다."),
  USER_NAME_FAILED(403, "U004", "이름의 형식이 맞지않습니다."),

  // Category
    CATEGORY_NOT_FOUND(400, "CT001", "잘못된 카테고리 입니다.")

    ;


  private final String code;
  private final String message;
  private int status;

  ErrorCode(final int status, final String code, final String message) {
    this.status = status;
    this.message = message;
    this.code = code;
  }

  public String getMessage() {
    return this.message;
  }

  public String getCode() {
    return code;
  }

  public int getStatus() {
    return status;
  }
}
