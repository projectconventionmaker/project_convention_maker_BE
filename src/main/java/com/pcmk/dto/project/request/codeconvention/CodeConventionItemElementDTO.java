package com.pcmk.dto.project.request.codeconvention;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CodeConventionItemElementDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String example;

    public static CodeConventionItemElementDTO of(String name, String example) {
        return CodeConventionItemElementDTO.builder()
                .name(name)
                .example(example)
                .build();
    }

    @Builder
    private CodeConventionItemElementDTO(String name, String example) {
        this.name = name;
        this.example = example;
    }
}
