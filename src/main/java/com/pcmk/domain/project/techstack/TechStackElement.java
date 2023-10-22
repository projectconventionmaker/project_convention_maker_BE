package com.pcmk.domain.project.techstack;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TechStackElement {

    private String category;
    private List<String> names;

    public static TechStackElement of(String category, List<String> names) {
        return TechStackElement.builder()
                .category(category)
                .names(names)
                .build();
    }

    @Builder
    private TechStackElement(String category, List<String> names) {
        this.category = category;
        this.names = names;
    }
}
