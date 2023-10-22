package com.pcmk.dto.project.commitconvention;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmk.domain.project.ProjectEntity;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CommitConventionDTO {

    @JsonProperty("elements")
    List<CommitConventionElementDTO> elementDTOs;

    public static CommitConventionDTO fromEntity(ProjectEntity entity) {
        if (Objects.isNull(entity.getCommitConvention()) ||
                Objects.isNull(entity.getCommitConvention().getElements())) {
            return null;
        }

        List<CommitConventionElementDTO> elementDTOs = entity.getCommitConvention().getElements().stream()
                .map(element -> CommitConventionElementDTO.of(element.getName(), element.isChecked()))
                .toList();

        return CommitConventionDTO.builder()
                .elementDTOs(elementDTOs)
                .build();
    }

    public static CommitConventionDTO of(List<CommitConventionElementDTO> elementDTOs) {
        return CommitConventionDTO.builder()
                .elementDTOs(elementDTOs)
                .build();
    }

    @Builder
    private CommitConventionDTO(List<CommitConventionElementDTO> elementDTOs) {
        this.elementDTOs = elementDTOs;
    }
}
