package org.ateam.ateam.global.error.handler;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ateam.ateam.global.error.ErrorCode;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Schema(description = "공통 에러 응답")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

  @Schema(description = "에러 메시지", example = "게시글을 찾을 수 없습니다.")
  private String message;

  @Schema(description = "HTTP 상태 코드", example = "404")
  private int status;

  @Schema(description = "필드 에러 목록")
  private List<FieldError> errors;

  @Schema(description = "비즈니스 에러 코드", example = "P001")
  private String code;

  private ErrorResponse(final ErrorCode code, final List<FieldError> errors) {
    this.message = code.getMessage();
    this.status = code.getStatus();
    this.errors = errors;
    this.code = code.getCode();
  }

  private ErrorResponse(final ErrorCode code) {
    this.message = code.getMessage();
    this.status = code.getStatus();
    this.code = code.getCode();
    this.errors = new ArrayList<>();
  }

  public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
    return new ErrorResponse(code, FieldError.of(bindingResult));
  }

  public static ErrorResponse of(final ErrorCode code) {
    return new ErrorResponse(code);
  }

  public static ErrorResponse of(final ErrorCode code, final List<FieldError> errors) {
    return new ErrorResponse(code, errors);
  }

  public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
    final String value = e.getValue() == null ? "" : e.getValue().toString();
    final List<FieldError> errors = FieldError.of(e.getName(), value, e.getErrorCode());
    return new ErrorResponse(ErrorCode.INVALID_TYPE_VALUE, errors);
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class FieldError {
    @Schema(description = "필드명", example = "title")
    private String field;

    @Schema(description = "거부된 값", example = "")
    private String value;

    @Schema(description = "오류 이유", example = "must not be blank")
    private String reason;

    private FieldError(final String field, final String value, final String reason) {
      this.field = field;
      this.value = value;
      this.reason = reason;
    }

    public static List<FieldError> of(final String field, final String value, final String reason) {
      List<FieldError> fieldErrors = new ArrayList<>();
      fieldErrors.add(new FieldError(field, value, reason));
      return fieldErrors;
    }

    private static List<FieldError> of(final BindingResult bindingResult) {
      final List<org.springframework.validation.FieldError> fieldErrors =
          bindingResult.getFieldErrors();
      return fieldErrors.stream()
          .map(
              error ->
                  new FieldError(
                      error.getField(),
                      error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                      error.getDefaultMessage()))
          .collect(Collectors.toList());
    }
  }
}
