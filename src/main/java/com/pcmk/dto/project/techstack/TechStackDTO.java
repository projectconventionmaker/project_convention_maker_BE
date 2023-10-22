package com.pcmk.dto.project.techstack;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmk.domain.project.ProjectEntity;
import com.pcmk.domain.project.techstack.TechStackElement;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TechStackDTO {

    @JsonProperty("elements")
    private List<TechStackElementDTO> elementDTOs;

    public static TechStackDTO fromEntity(ProjectEntity entity) {
        List<TechStackElement> elements = entity.getTechStack().getElements();
        if (Objects.isNull(elements)) {
            return null;
        }

        List<TechStackElementDTO> elementDTOs = elements.stream()
                .map(element -> TechStackElementDTO.of(element.getCategory(), element.getNames()))
                .toList();

        return TechStackDTO.builder()
                .elementDTOs(elementDTOs)
                .build();
    }

    public static TechStackDTO of(List<TechStackElementDTO> elementDTOs) {
        return TechStackDTO.builder()
                .elementDTOs(elementDTOs)
                .build();
    }

    @Builder
    private TechStackDTO(List<TechStackElementDTO> elementDTOs) {
        this.elementDTOs = elementDTOs;
    }
}
