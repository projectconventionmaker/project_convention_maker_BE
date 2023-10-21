package com.pcmk.dto.project.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProjectCreateRequestDTO {

    @NotBlank
    @JsonProperty("project_name")
    private String projectName;

    @Builder
    private ProjectCreateRequestDTO(String projectName) {
        this.projectName = projectName;
    }
}
