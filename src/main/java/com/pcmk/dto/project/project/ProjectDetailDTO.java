package com.pcmk.dto.project.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmk.domain.project.ProjectEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProjectDetailDTO {

    @JsonProperty("project_name")
    private String projectName;

    @JsonProperty("team_name")
    private String teamName;

    @JsonProperty("project_uuid")
    private String projectUUID;

    private String introduction;

    private String detail;

    public static ProjectDetailDTO fromEntity(ProjectEntity entity) {
        return ProjectDetailDTO.builder()
                .entity(entity)
                .build();
    }

    @Builder
    private ProjectDetailDTO(ProjectEntity entity) {
        this.projectName = entity.getProjectName();
        this.projectUUID = entity.getProjectUUID();
        this.teamName = entity.getTeamName();
        this.introduction = entity.getIntroduction();
        this.detail = entity.getDetail();
    }
}
