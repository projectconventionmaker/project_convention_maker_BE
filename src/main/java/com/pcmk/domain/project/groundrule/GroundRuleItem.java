package com.pcmk.domain.project.groundrule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GroundRuleItem {
    @NotBlank
    private String name;

    @NotNull
    private boolean checked;
}
