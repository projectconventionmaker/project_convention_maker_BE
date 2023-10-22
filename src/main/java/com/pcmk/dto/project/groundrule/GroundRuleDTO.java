package com.pcmk.dto.project.groundrule;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmk.domain.project.ProjectEntity;
import com.pcmk.domain.project.groundrule.GroundRuleElement;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GroundRuleDTO {

    @JsonProperty("elements")
    List<GroundRuleElementDTO> elementDTOs;

    public static GroundRuleDTO fromEntity(ProjectEntity entity) {
        List<GroundRuleElement> elements = entity.getGroundRule().getElements();
        if (Objects.isNull(elements)) {
            return null;
        }

        List<GroundRuleElementDTO> elementDTOs = elements.stream()
                .map(element -> GroundRuleElementDTO.of(element.getName(), element.isChecked()))
                .toList();

        return GroundRuleDTO.builder()
                .elementDTOs(elementDTOs)
                .build();
    }

    public static GroundRuleDTO of(List<GroundRuleElementDTO> elementDTOs) {
        return GroundRuleDTO.builder()
                .elementDTOs(elementDTOs)
                .build();
    }

    @Builder
    private GroundRuleDTO(List<GroundRuleElementDTO> elementDTOs) {
        this.elementDTOs = elementDTOs;
    }
}
