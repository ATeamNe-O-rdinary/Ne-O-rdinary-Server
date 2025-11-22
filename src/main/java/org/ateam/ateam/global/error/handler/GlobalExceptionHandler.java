package org.ateam.ateam.global.error.handler;

import java.nio.file.AccessDeniedException;

import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;
import org.ateam.ateam.global.logging.LoggingUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e
  ) {
    LoggingUtils.warn(e);

    final ErrorResponse response =
        ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException e
  ) {
    LoggingUtils.warn(e);

    final ErrorResponse response = ErrorResponse.of(e);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e
  ) {
    LoggingUtils.warn(e);

    final ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
    return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
  }

  @ExceptionHandler(AccessDeniedException.class)
  protected ResponseEntity<ErrorResponse> handleAccessDeniedException(
      AccessDeniedException e
  ) {
    LoggingUtils.warn(e);

    final ErrorResponse response = ErrorResponse.of(ErrorCode.HANDLE_ACCESS_DENIED);
    return new ResponseEntity<>(
        response,
        HttpStatus.valueOf(ErrorCode.HANDLE_ACCESS_DENIED.getStatus())
    );
  }

  @ExceptionHandler(BusinessException.class)
  protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {

    LoggingUtils.warn(e);

    final ErrorCode errorCode = e.getErrorCode();
    final ErrorResponse response = ErrorResponse.of(errorCode);

    return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorResponse> handleException(Exception e) {

    LoggingUtils.error(new RuntimeException(e));

    final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
