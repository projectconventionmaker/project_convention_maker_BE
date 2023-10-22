package com.pcmk.dto.project.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmk.domain.project.ProjectEntity;
import com.pcmk.domain.project.teammate.Teammate;
import com.pcmk.domain.project.teammate.TeammateElement;
import com.pcmk.dto.project.teammate.TeammateElementDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

@NoArgsConstructor
@Data
public class ProjectUpdateRequestDTO {

    @NotBlank
    @JsonProperty("project_name")
    private String projectName;

    @NotBlank
    @JsonProperty("team_name")
    private String teamName;

    @NotBlank
    @JsonProperty("introduction")
    private String introduction;

    @NotBlank
    @JsonProperty("detail")
    private String detail;

    @NotNull
    @JsonProperty("project_start")
    private LocalDate startAt;

    @NotNull
    @JsonProperty("project_end")
    private LocalDate endAt;

    @NotNull
    @JsonProperty("teammates")
    private List<TeammateElementDTO> teammateElementDTOs;

    public ProjectEntity toEntity() {
        return ProjectEntity.builder()
                .projectName(this.projectName)
                .teamName(this.teamName)
                .introduction(this.introduction)
                .detail(this.detail)
                .startAt(this.startAt)
                .endAt(this.endAt)
                .teammate(Teammate.of(getTeammateElements()))
                .build();
    }

    @Builder
    private ProjectUpdateRequestDTO(String projectName,
                                    String teamName,
                                    String introduction,
                                    String detail,
                                    LocalDate startAt,
                                    LocalDate endAt,
                                    List<TeammateElementDTO> teammateElementDTOs) {
        this.projectName = projectName;
        this.teamName = teamName;
        this.introduction = introduction;
        this.detail = detail;
        this.startAt = startAt;
        this.endAt = endAt;
        this.teammateElementDTOs = teammateElementDTOs;
    }

    private List<TeammateElement> getTeammateElements() {
        if (CollectionUtils.isEmpty(this.teammateElementDTOs)) {
            return Collections.emptyList();
        }
        return this.teammateElementDTOs.stream()
                .map(element -> TeammateElement.of(element.getName(), element.getPosition(), element.getLink()))
                .toList();
    }
}
