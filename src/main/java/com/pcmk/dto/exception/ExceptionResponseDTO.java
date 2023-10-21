package com.pcmk.dto.exception;

import lombok.Data;

@Data
public class ExceptionResponseDTO<T> {

    private int code;
    private String message;
    private T data;

    private ExceptionResponseDTO(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ExceptionResponseDTO<T> of(int code, String message, T data) {
        return new ExceptionResponseDTO<>(code, message, data);
    }
}
