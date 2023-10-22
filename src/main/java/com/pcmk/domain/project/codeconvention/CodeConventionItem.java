package com.pcmk.domain.project.codeconvention;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CodeConventionItem {

    private String category;
    private List<CodeConventionItemElement> codeConventionItemElementList;

    public static CodeConventionItem of(String category,
                                        List<CodeConventionItemElement> codeConventionItemElementList) {
        return CodeConventionItem.builder()
                .category(category)
                .codeConventionItemElementList(codeConventionItemElementList)
                .build();
    }

    @Builder
    private CodeConventionItem(String category, List<CodeConventionItemElement> codeConventionItemElementList) {
        this.category = category;
        this.codeConventionItemElementList = codeConventionItemElementList;
    }
}
