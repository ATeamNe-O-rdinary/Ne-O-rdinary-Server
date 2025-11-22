package org.ateam.ateam.global.logging;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingUtils {

  public static void warn(Exception exception) {
    log.warn(formatMessage(exception));
  }

  public static void error(RuntimeException exception) {
    log.error(formatMessage(exception));
  }

  private static String formatMessage(Exception exception) {
    String message = exception.getMessage();
    return (message == null || message.isBlank()) ? "" : message;
  }
}
