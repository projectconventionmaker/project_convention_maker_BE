package com.pcmk.domain.project.codeconvention;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CodeConvention {

    List<CodeConventionElement> elements;

    public static CodeConvention of(List<CodeConventionElement> elements) {
        return CodeConvention.builder()
                .elements(elements)
                .build();
    }

    @Builder
    private CodeConvention(List<CodeConventionElement> elements) {
        this.elements = elements;
    }
}
