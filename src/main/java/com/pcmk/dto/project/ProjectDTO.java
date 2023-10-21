package com.pcmk.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmk.domain.project.ProjectEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProjectDTO {

    @JsonProperty("project_name")
    private String projectName;

    @JsonProperty("project_uuid")
    private String projectUuid;

    public static ProjectDTO fromEntity(ProjectEntity projectEntity) {
        return ProjectDTO.builder()
                .projectName(projectEntity.getProjectName())
                .projectUuid(projectEntity.getProjectUuid())
                .build();
    }

    @Builder
    private ProjectDTO(String projectName, String projectUuid) {
        this.projectName = projectName;
        this.projectUuid = projectUuid;
    }
}
