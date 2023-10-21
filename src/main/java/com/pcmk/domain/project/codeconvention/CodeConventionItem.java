package com.pcmk.domain.project.codeconvention;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CodeConventionItem {
    @NotBlank
    private String name;
}
