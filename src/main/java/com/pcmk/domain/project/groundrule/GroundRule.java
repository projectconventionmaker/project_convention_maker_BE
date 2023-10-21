package com.pcmk.domain.project.groundrule;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GroundRule {
    List<GroundRuleItem> items;

    public static GroundRule of(List<GroundRuleItem> items) {
        return GroundRule.builder()
                .items(items)
                .build();
    }

    @Builder
    private GroundRule(List<GroundRuleItem> items) {
        this.items = items;
    }
}
