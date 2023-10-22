package com.pcmk.domain.project.techstack;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TechStack {

    private List<TechStackElement> elements;

    public static TechStack of(List<TechStackElement> elements) {
        return TechStack.builder()
                .elements(elements)
                .build();
    }

    @Builder
    private TechStack(List<TechStackElement> elements) {
        this.elements = elements;
    }
}
