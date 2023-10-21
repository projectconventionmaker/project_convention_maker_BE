package com.pcmk.domain.project.techstack;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TechStack {

    private List<ProgrammingLanguage> languages;
    private List<Framework> frameworks;
    private List<Style> styles;

    @Builder
    private TechStack(List<ProgrammingLanguage> languages, List<Framework> frameworks, List<Style> styles) {
        this.languages = languages;
        this.frameworks = frameworks;
        this.styles = styles;
    }
}
