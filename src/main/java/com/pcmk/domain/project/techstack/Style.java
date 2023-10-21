package com.pcmk.domain.project.techstack;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Style {
    @NotBlank
    private String name;
}
