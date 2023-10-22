package com.pcmk.dto.project.commitconvention;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmk.domain.project.commitconvention.CommitConvention;
import com.pcmk.domain.project.commitconvention.CommitConventionElement;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CommitConventionUpdateRequestDTO {

    @NotNull
    @JsonProperty("code_conventions")
    List<CommitConventionElementDTO> elementDTOs;

    public CommitConvention toEntity() {
        List<CommitConventionElement> elements = elementDTOs.stream()
                .map(elementDTO -> CommitConventionElement.of(elementDTO.getName(), elementDTO.isChecked()))
                .toList();

        return CommitConvention.of(elements);
    }

    public static CommitConventionUpdateRequestDTO of(List<CommitConventionElementDTO> elementDTOs) {
        return CommitConventionUpdateRequestDTO.builder()
                .elementDTOs(elementDTOs)
                .build();
    }

    @Builder
    private CommitConventionUpdateRequestDTO(List<CommitConventionElementDTO> elementDTOs) {
        this.elementDTOs = elementDTOs;
    }
}
