package com.pcmk.domain.project.codeconvention;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CodeConventionItemElement {

    private String name;
    private String example;

    public static CodeConventionItemElement of(String name, String example) {
        return CodeConventionItemElement.builder()
                .name(name)
                .example(example)
                .build();
    }

    @Builder
    private CodeConventionItemElement(String name, String example) {
        this.name = name;
        this.example = example;
    }
}
