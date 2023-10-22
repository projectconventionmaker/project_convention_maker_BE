package com.pcmk.domain.project.groundrule;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GroundRule {

    List<GroundRuleElement> elements;

    public static GroundRule of(List<GroundRuleElement> elements) {
        return GroundRule.builder()
                .elements(elements)
                .build();
    }

    @Builder
    private GroundRule(List<GroundRuleElement> elements) {
        this.elements = elements;
    }
}
