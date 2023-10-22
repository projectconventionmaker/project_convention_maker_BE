package com.pcmk.dto.project.teammate;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TeammateDTO {

    @JsonProperty("teammates")
    private List<TeammateElementDTO> teammateElementDTOs;

    public static TeammateDTO of(List<TeammateElementDTO> teammateElementDTOs) {
        return TeammateDTO.builder()
                .teammateElementDTOs(teammateElementDTOs)
                .build();
    }

    @Builder
    private TeammateDTO(List<TeammateElementDTO> teammateElementDTOs) {
        this.teammateElementDTOs = teammateElementDTOs;
    }
}
