package com.pcmk.dto.project.request.techstack;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmk.domain.project.techstack.TechStack;
import com.pcmk.domain.project.techstack.TechStackElement;
import com.pcmk.dto.project.TechStackElementDTO;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TechStackUpdateRequestDTO {

    @NotNull
    @JsonProperty("tech_stack")
    private List<TechStackElementDTO> techStackElementDTOList;

    public static TechStackUpdateRequestDTO of(TechStack techStack) {
        List<TechStackElementDTO> techStackElementDTOList = new ArrayList<>();

        for (TechStackElement techStackElement : techStack.getTechStackElmentList()) {
            techStackElementDTOList.add(
                    TechStackElementDTO.of(techStackElement.getCategory(), techStackElement.getNames()));
        }

        return TechStackUpdateRequestDTO.builder()
                .techStackElementDTOList(techStackElementDTOList)
                .build();
    }

    public List<TechStackElement> toTechList() {
        List<TechStackElement> techList = new ArrayList<>();
        for (TechStackElementDTO techStackElementDTO : techStackElementDTOList) {
            techList.add(TechStackElement.of(techStackElementDTO.getCategory(), techStackElementDTO.getNames()));
        }
        return techList;
    }

    @Builder
    private TechStackUpdateRequestDTO(List<TechStackElementDTO> techStackElementDTOList) {
        this.techStackElementDTOList = techStackElementDTOList;
    }
}
