package com.pcmk.domain.project.techstack;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProgrammingLanguage {
    @NotBlank
    private String name;
}
