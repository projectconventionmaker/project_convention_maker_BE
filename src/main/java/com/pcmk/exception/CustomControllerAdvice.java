package com.pcmk.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse<Void>> handle(CustomException ex) {
        doLog(ex, "CustomException while processing request.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ExceptionResponse<Void>> handle(BindException ex) {
        doLog(ex, "BindException while binding request parameters.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse<Void>> handle(RuntimeException ex) {
        log.error("Error in [{}] Cause: [{}]", ex.getStackTrace()[0], ex.getMessage(), ex);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.of(500, "Internal Server Error", null));
    }

    private void doLog(Exception e, String description) {
        log.error("Error in [{}]: [{}] Cause: [{}]", e.getStackTrace()[0], description, e.getMessage());
    }
}
