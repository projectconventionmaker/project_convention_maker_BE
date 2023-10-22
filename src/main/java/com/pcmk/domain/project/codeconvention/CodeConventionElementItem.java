package com.pcmk.domain.project.codeconvention;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CodeConventionElementItem {

    private String name;
    private String example;

    public static CodeConventionElementItem of(String name, String example) {
        return CodeConventionElementItem.builder()
                .name(name)
                .example(example)
                .build();
    }

    @Builder
    private CodeConventionElementItem(String name, String example) {
        this.name = name;
        this.example = example;
    }
}
