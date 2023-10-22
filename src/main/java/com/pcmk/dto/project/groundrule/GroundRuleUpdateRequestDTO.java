package com.pcmk.dto.project.groundrule;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmk.domain.project.groundrule.GroundRule;
import com.pcmk.domain.project.groundrule.GroundRuleElement;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GroundRuleUpdateRequestDTO {

    @NotNull
    @JsonProperty("ground_rules")
    List<GroundRuleElementDTO> elementDTOs;

    public GroundRule toEntity() {
        List<GroundRuleElement> elements = elementDTOs.stream()
                .map(dto -> GroundRuleElement.of(dto.getName(), dto.isChecked()))
                .toList();

        return GroundRule.of(elements);
    }

    public static GroundRuleUpdateRequestDTO of(List<GroundRuleElementDTO> elementDTOs) {
        return GroundRuleUpdateRequestDTO.builder()
                .elementDTOs(elementDTOs)
                .build();
    }

    @Builder
    private GroundRuleUpdateRequestDTO(List<GroundRuleElementDTO> elementDTOs) {
        this.elementDTOs = elementDTOs;
    }
}
