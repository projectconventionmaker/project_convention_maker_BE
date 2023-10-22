package com.pcmk.dto.project.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmk.domain.project.techstack.TechStack;
import com.pcmk.domain.project.techstack.TechStackElement;
import com.pcmk.dto.project.TechStackElementDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TechStackUpdateResponseDTO {

    @JsonProperty("tech_stack")
    List<TechStackElementDTO> techStackElementDTOList;

    public static TechStackUpdateResponseDTO of(TechStack techStack) {
        List<TechStackElementDTO> techStackElementDTOList = new ArrayList<>();

        for (TechStackElement techStackElement : techStack.getTechStackElmentList()) {
            techStackElementDTOList.add(
                    TechStackElementDTO.of(techStackElement.getCategory(), techStackElement.getNames()));
        }

        return TechStackUpdateResponseDTO.builder()
                .techStackElementDTOList(techStackElementDTOList)
                .build();
    }

    @Builder
    private TechStackUpdateResponseDTO(List<TechStackElementDTO> techStackElementDTOList) {
        this.techStackElementDTOList = techStackElementDTOList;
    }
}
