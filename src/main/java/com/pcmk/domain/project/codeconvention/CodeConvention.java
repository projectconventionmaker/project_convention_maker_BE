package com.pcmk.domain.project.codeconvention;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CodeConvention {
    List<CodeConventionItem> items;

    public static CodeConvention of(List<CodeConventionItem> items) {
        return CodeConvention.builder()
                .items(items)
                .build();
    }
    
    @Builder
    private CodeConvention(List<CodeConventionItem> items) {
        this.items = items;
    }
}
