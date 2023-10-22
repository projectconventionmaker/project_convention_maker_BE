package com.pcmk.domain.project.groundrule;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GroundRuleElement {

    private String name;
    private boolean checked;

    public static GroundRuleElement of(String name, boolean checked) {
        return GroundRuleElement.builder()
                .name(name)
                .checked(checked)
                .build();
    }

    @Builder
    private GroundRuleElement(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }
}
