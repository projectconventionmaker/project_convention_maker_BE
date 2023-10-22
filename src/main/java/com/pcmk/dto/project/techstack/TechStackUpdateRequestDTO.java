package com.pcmk.dto.project.techstack;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmk.domain.project.techstack.TechStack;
import com.pcmk.domain.project.techstack.TechStackElement;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TechStackUpdateRequestDTO {

    @NotNull
    @JsonProperty("tech_stack")
    private List<TechStackElementDTO> techStackElementDTOs;

    public TechStack toEntity() {
        List<TechStackElement> elements = techStackElementDTOs.stream()
                .map(dto -> TechStackElement.of(dto.getCategory(), dto.getNames()))
                .toList();
        return TechStack.of(elements);
    }

    public static TechStackUpdateRequestDTO of(List<TechStackElementDTO> techStackElementDTOs) {
        return TechStackUpdateRequestDTO.builder()
                .techStackElementDTOs(techStackElementDTOs)
                .build();
    }

    @Builder
    private TechStackUpdateRequestDTO(List<TechStackElementDTO> techStackElementDTOs) {
        this.techStackElementDTOs = techStackElementDTOs;
    }
}
