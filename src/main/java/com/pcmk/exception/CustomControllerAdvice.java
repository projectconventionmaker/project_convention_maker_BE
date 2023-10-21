package com.pcmk.exception;

import static com.pcmk.exception.CustomExceptionError.INTERNAL_SERVER_ERROR;
import static com.pcmk.exception.CustomExceptionError.INVALID_ARGUMENT;
import static com.pcmk.exception.CustomExceptionError.METHOD_NOT_ALLOWED;
import static com.pcmk.exception.CustomExceptionError.NOT_FOUND;

import com.pcmk.dto.exception.ExceptionResponseDTO;
import com.pcmk.dto.exception.ValidationErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponseDTO<Void>> handle(CustomException ex) {
        doLog(ex, "CustomException while processing request.");
        return ResponseEntity.status(ex.error().status()).body(
                ExceptionResponseDTO.of(ex.error().code(), ex.message(), null));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ExceptionResponseDTO<ValidationErrorDTO>> handle(BindException ex) {
        doLog(ex, "BindException while binding request parameters.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ExceptionResponseDTO.of(INVALID_ARGUMENT.code(), INVALID_ARGUMENT.message(),
                        ValidationErrorDTO.of(ex.getBindingResult())));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponseDTO<Void>> handle(IllegalArgumentException ex) {
        doLog(ex, "IllegalArgumentException while processing arguments.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ExceptionResponseDTO.of(INVALID_ARGUMENT.code(), INVALID_ARGUMENT.message(), null));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponseDTO<Void>> handle(HttpMessageNotReadableException ex) {
        doLog(ex, "HttpMessageNotReadableException while reading the HTTP message.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ExceptionResponseDTO.of(INVALID_ARGUMENT.code(), INVALID_ARGUMENT.message(), null));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponseDTO<Void>> handle(MethodArgumentTypeMismatchException ex) {
        doLog(ex, "MethodArgumentTypeMismatchException while matching argument types.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ExceptionResponseDTO.of(INVALID_ARGUMENT.code(), INVALID_ARGUMENT.message(), null));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionResponseDTO<Void>> handle(NoHandlerFoundException ex) {
        doLog(ex, "NoHandlerFoundException for the requested route.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ExceptionResponseDTO.of(NOT_FOUND.code(), NOT_FOUND.message(), null));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponseDTO<Void>> handle(HttpRequestMethodNotSupportedException ex) {
        doLog(ex, "HttpRequestMethodNotSupportedException for the requested method.");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                ExceptionResponseDTO.of(METHOD_NOT_ALLOWED.code(), METHOD_NOT_ALLOWED.message(), null));
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ExceptionResponseDTO<Void>> handle(HttpMediaTypeNotAcceptableException ex) {
        doLog(ex, "HttpMediaTypeNotAcceptableException for the requested method.");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                ExceptionResponseDTO.of(INVALID_ARGUMENT.code(), INVALID_ARGUMENT.message(), null));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionResponseDTO<Void>> handle(MissingServletRequestParameterException ex) {
        doLog(ex, "MissingServletRequestParameterException while processing request parameters.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ExceptionResponseDTO.of(INVALID_ARGUMENT.code(), INVALID_ARGUMENT.message(), null));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponseDTO<Void>> handle(RuntimeException ex) {
        log.error("Error in [{}] Cause: [{}]", ex.getStackTrace()[0], ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ExceptionResponseDTO.of(INTERNAL_SERVER_ERROR.code(),
                        INTERNAL_SERVER_ERROR.message(), null));
    }

    private void doLog(Exception e, String description) {
        log.error("Error in [{}]: [{}] Cause: [{}]", e.getStackTrace()[0], description, e.getMessage());
    }

}
