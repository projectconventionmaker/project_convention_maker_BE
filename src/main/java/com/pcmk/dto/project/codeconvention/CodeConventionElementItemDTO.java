package com.pcmk.dto.project.codeconvention;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CodeConventionElementItemDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String example;

    public static CodeConventionElementItemDTO of(String name, String example) {
        return CodeConventionElementItemDTO.builder()
                .name(name)
                .example(example)
                .build();
    }

    @Builder
    private CodeConventionElementItemDTO(String name, String example) {
        this.name = name;
        this.example = example;
    }
}
