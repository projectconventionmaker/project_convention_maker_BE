package com.pcmk.domain.project.codeconvention;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CodeConventionElement {

    private String category;
    private List<CodeConventionElementItem> items;

    public static CodeConventionElement of(String category,
                                           List<CodeConventionElementItem> items) {
        return CodeConventionElement.builder()
                .category(category)
                .items(items)
                .build();
    }

    @Builder
    private CodeConventionElement(String category, List<CodeConventionElementItem> items) {
        this.category = category;
        this.items = items;
    }
}
