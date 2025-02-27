package pl.miloszgilga.pmwp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(NoResourceFoundException.class)
  ResponseEntity<Void> handleNotFound() {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<Void> handleUnknownException(Exception ex) {
    log.error(ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }
}
