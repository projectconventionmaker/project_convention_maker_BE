package com.pcmk.dto.project.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProjectCreateRequestDTO {

    @JsonProperty("project_name")
    @NotBlank(message = "프로젝트 이름이 비어있으면 안됩니다.")
    private String projectName;

    @Builder
    private ProjectCreateRequestDTO(String projectName) {
        this.projectName = projectName;
    }
}
