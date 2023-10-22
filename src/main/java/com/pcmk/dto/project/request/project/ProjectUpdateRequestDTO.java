package com.pcmk.dto.project.request.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Builder
    private ProjectUpdateRequestDTO(String projectName, String teamName, String introduction, String detail) {
        this.projectName = projectName;
        this.teamName = teamName;
        this.introduction = introduction;
        this.detail = detail;
    }
}
