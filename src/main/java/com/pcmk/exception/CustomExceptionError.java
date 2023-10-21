package com.pcmk.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum CustomExceptionError {

    // 400-499: 기본 HTTP 에러
    INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, 400, "파라미터가 올바르지 않습니다"),
    FORBIDDEN(HttpStatus.FORBIDDEN, 403, "권한이 없습니다"),
    NOT_FOUND(HttpStatus.NOT_FOUND, 404, "존재하지 않는 리소스에 대한 요청입니다"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, 405, "올바르지 않은 요청 메소드입니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 내부 오류입니다"),

    // 1000-1099: 프로젝트 관련 에러
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, 1000, "존재하지 않는 프로젝트입니다"),
    PROJECT_NAME_DUPLICATED(HttpStatus.CONFLICT, 1001, "이미 존재하는 프로젝트 이름입니다");

    private final HttpStatus httpStatus;
    private final int errorCode;
    private final String errorMsg;

    public int code() {
        return this.errorCode;
    }

    public String message() {
        return this.errorMsg;
    }

    public HttpStatus status() {
        return this.httpStatus;
    }
}
