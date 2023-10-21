package com.pcmk.domain.project.groundrule;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GroundRule {
    List<GroundRuleItem> items;

    @Builder
    private GroundRule(List<GroundRuleItem> items) {
        this.items = items;
    }
}
