package com.pcmk.dto.project.codeconvention;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmk.domain.project.ProjectEntity;
import com.pcmk.domain.project.codeconvention.CodeConventionElement;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CodeConventionDTO {

    @JsonProperty("elements")
    List<CodeConventionElementDTO> elementDTOs;

    public static CodeConventionDTO fromEntity(ProjectEntity entity) {
        List<CodeConventionElement> elements = entity.getCodeConvention().getElements();
        if (Objects.isNull(elements)) {
            return null;
        }

        List<CodeConventionElementDTO> elementDTOs = elements.stream()
                .map(element -> CodeConventionElementDTO.of(element.getCategory(), element.getItems().stream()
                        .map(item -> CodeConventionElementItemDTO.of(item.getName(), item.getExample()))
                        .toList()))
                .toList();

        return CodeConventionDTO.builder()
                .elementDTOs(elementDTOs)
                .build();
    }

    public static CodeConventionDTO of(List<CodeConventionElementDTO> elementDTOs) {
        return CodeConventionDTO.builder()
                .elementDTOs(elementDTOs)
                .build();
    }

    @Builder
    private CodeConventionDTO(List<CodeConventionElementDTO> elementDTOs) {
        this.elementDTOs = elementDTOs;
    }
}
