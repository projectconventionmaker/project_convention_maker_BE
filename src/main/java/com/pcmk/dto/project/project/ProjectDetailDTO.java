package com.pcmk.dto.project.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmk.domain.project.ProjectEntity;
import com.pcmk.dto.project.teammate.TeammateElementDTO;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

    @JsonProperty("project_start")
    private LocalDate startAt;

    @JsonProperty("project_end")
    private LocalDate endAt;

    private String detail;

    @JsonProperty("teammates")
    private List<TeammateElementDTO> teammateElementDTOs;

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
        this.startAt = entity.getStartAt();
        this.endAt = entity.getEndAt();
        this.teammateElementDTOs = getTeammates(entity);
    }

    private List<TeammateElementDTO> getTeammates(ProjectEntity entity) {
        if (Objects.isNull(entity.getTeammate()) || Objects.isNull(entity.getTeammate().getElements())) {
            return Collections.emptyList();
        }
        return entity.getTeammate().getElements().stream()
                .map(element -> TeammateElementDTO.of(element.getId(), element.getName(), element.getPosition(),
                        element.getLink()))
                .toList();
    }
}
