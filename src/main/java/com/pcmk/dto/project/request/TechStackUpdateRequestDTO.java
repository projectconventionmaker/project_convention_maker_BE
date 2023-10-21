package com.pcmk.dto.project.request;

import com.pcmk.domain.project.techstack.Framework;
import com.pcmk.domain.project.techstack.ProgrammingLanguage;
import com.pcmk.domain.project.techstack.Style;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TechStackUpdateRequestDTO {

    @NotNull
    private List<ProgrammingLanguage> languages;
    @NotNull
    private List<Framework> frameworks;
    @NotNull
    private List<Style> styles;

    @Builder
    private TechStackUpdateRequestDTO(List<ProgrammingLanguage> languages,
                                      List<Framework> frameworks,
                                      List<Style> styles) {
        this.languages = languages;
        this.frameworks = frameworks;
        this.styles = styles;
    }
}
