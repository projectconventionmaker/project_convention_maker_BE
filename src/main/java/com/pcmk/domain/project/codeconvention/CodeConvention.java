package com.pcmk.domain.project.codeconvention;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CodeConvention {

    List<CodeConventionItem> codeConventionItems;

    public static CodeConvention of(List<CodeConventionItem> codeConventionItems) {
        return CodeConvention.builder()
                .codeConventionItems(codeConventionItems)
                .build();
    }

    @Builder
    private CodeConvention(List<CodeConventionItem> codeConventionItems) {
        this.codeConventionItems = codeConventionItems;
    }
}
