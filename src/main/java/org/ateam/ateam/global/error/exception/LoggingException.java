package org.ateam.ateam.global.error.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoggingException extends RuntimeException {
  private final HttpStatus status;
  private final String message;
}
