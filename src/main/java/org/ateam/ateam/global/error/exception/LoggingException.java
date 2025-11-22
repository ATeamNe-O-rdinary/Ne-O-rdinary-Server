package org.ateam.ateam.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class LoggingException extends RuntimeException {
  private final HttpStatus status;
  private final String message;
}
