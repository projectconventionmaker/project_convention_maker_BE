package com.pcmk.dto.project.techstack;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TechStackElementDTO {

    @NotBlank
    private String category;

    @NotNull
    private List<String> names;

    public static TechStackElementDTO of(String category, List<String> names) {
        return TechStackElementDTO.builder()
                .category(category)
                .names(names)
                .build();
    }

    @Builder
    private TechStackElementDTO(String category, List<String> names) {
        this.category = category;
        this.names = names;
    }
}
