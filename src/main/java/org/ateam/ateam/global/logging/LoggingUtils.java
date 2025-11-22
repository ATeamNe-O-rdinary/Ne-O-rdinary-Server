package org.ateam.ateam.global.logging;

import org.apache.logging.log4j.LoggingException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingUtils {

  public static void warn(LoggingException exception) {
    String message = getExceptionMessage(exception.getMessage());
    log.warn(message + "\n \t {}", exception);
  }

  public static void warn(MethodArgumentNotValidException exception) {
    String message = getExceptionMessage(exception.getMessage());
    log.warn(message + "\n \t {}", exception);
  }

  public static void warn(Exception exception) {
    String message = getExceptionMessage(exception.getMessage());
    log.warn(message + "\n \t {}", exception);
  }

  public static void error(RuntimeException exception) {
    String message = getExceptionMessage(exception.getMessage());
    log.error(message + "\n \t {}", exception);
  }

  private static String getExceptionMessage(String message) {
    if (message == null || message.isBlank()) {
      return "";
    }
    return message;
  }
}
