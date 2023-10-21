package com.pcmk.dto.exception;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@NoArgsConstructor
@Getter
@Setter
public class ValidationErrorDTO {

    private List<FieldErrorDTO> fieldErrors;

    public static ValidationErrorDTO of(BindingResult bindingResult) {
        List<FieldErrorDTO> fieldErrors = bindingResult.getFieldErrors().stream()
                .map(FieldErrorDTO::of)
                .toList();

        return ValidationErrorDTO.builder()
                .fieldErrors(fieldErrors)
                .build();
    }

    @Builder
    private ValidationErrorDTO(List<FieldErrorDTO> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class FieldErrorDTO {
        private String field;
        private String message;

        public static FieldErrorDTO of(FieldError fieldError) {
            return FieldErrorDTO.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }

        @Builder
        private FieldErrorDTO(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}
