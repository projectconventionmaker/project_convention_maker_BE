package com.pcmk.exception;

import lombok.Data;

@Data
public class ExceptionResponse<T> {

    private int code;
    private String message;
    private T data;

    private ExceptionResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ExceptionResponse<T> of(int code, String message, T data) {
        return new ExceptionResponse<>(code, message, data);
    }
}
