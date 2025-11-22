package org.ateam.ateam.global.dto;

import java.util.Optional;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseDto<T> {

  private final int status;
  private final String code;
  private final T data;
  private final String message;

  private ResponseDto(int status, String code, T data, String message) {
    this.status = status;
    this.code = code;
    this.data = data;
    this.message = message;
  }

  public static <T> ResponseDto<T> of(HttpStatus httpStatus, String code, String message) {
    int status = Optional.ofNullable(httpStatus).orElse(HttpStatus.OK).value();

    return new ResponseDto<>(status, code, null, message);
  }

  public static <T> ResponseDto<T> of(HttpStatus httpStatus, String code, String message, T data) {
    int status = Optional.ofNullable(httpStatus).orElse(HttpStatus.OK).value();

    return new ResponseDto<>(status, code, data, message);
  }
}
