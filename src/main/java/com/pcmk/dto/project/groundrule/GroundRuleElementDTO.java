package com.pcmk.dto.project.groundrule;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GroundRuleElementDTO {
    private String name;
    private boolean checked;

    public static GroundRuleElementDTO of(String name, boolean checked) {
        return GroundRuleElementDTO.builder()
                .name(name)
                .checked(checked)
                .build();
    }

    @Builder
    private GroundRuleElementDTO(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }
}
