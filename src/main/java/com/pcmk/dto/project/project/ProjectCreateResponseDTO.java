package com.pcmk.dto.project.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmk.domain.project.ProjectEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProjectCreateResponseDTO {

    @JsonProperty("project_detail")
    private ProjectDetailDTO projectDetailDTO;

    public static ProjectCreateResponseDTO fromEntity(ProjectEntity entity) {
        return ProjectCreateResponseDTO.builder()
                .projectDetailDTO(ProjectDetailDTO.fromEntity(entity))
                .build();
    }

    @Builder
    private ProjectCreateResponseDTO(ProjectDetailDTO projectDetailDTO) {
        this.projectDetailDTO = projectDetailDTO;
    }
}
