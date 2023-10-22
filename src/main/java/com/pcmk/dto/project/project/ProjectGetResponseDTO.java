package com.pcmk.dto.project.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmk.domain.project.ProjectEntity;
import com.pcmk.dto.project.codeconvention.CodeConventionDTO;
import com.pcmk.dto.project.commitconvention.CommitConventionDTO;
import com.pcmk.dto.project.groundrule.GroundRuleDTO;
import com.pcmk.dto.project.techstack.TechStackDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProjectGetResponseDTO {

    @JsonProperty("project_detail")
    private ProjectDetailDTO projectDetailDTO;

    @JsonProperty("tech_stack")
    private TechStackDTO techStackDTO;

    @JsonProperty("ground_rule")
    private GroundRuleDTO groundRuleDTO;

    @JsonProperty("code_convention")
    private CodeConventionDTO codeConventionDTO;

    @JsonProperty("commit_convention")
    private CommitConventionDTO commitConventionDTO;

    public static ProjectGetResponseDTO fromEntity(ProjectEntity entity) {
        return ProjectGetResponseDTO.builder()
                .projectDetailDTO(ProjectDetailDTO.fromEntity(entity))
                .techStackDTO(TechStackDTO.fromEntity(entity))
                .groundRuleDTO(GroundRuleDTO.fromEntity(entity))
                .codeConventionDTO(CodeConventionDTO.fromEntity(entity))
                .commitConventionDTO(CommitConventionDTO.fromEntity(entity))
                .build();
    }
    
    @Builder
    private ProjectGetResponseDTO(ProjectDetailDTO projectDetailDTO, TechStackDTO techStackDTO,
                                  GroundRuleDTO groundRuleDTO,
                                  CodeConventionDTO codeConventionDTO, CommitConventionDTO commitConventionDTO) {
        this.projectDetailDTO = projectDetailDTO;
        this.techStackDTO = techStackDTO;
        this.groundRuleDTO = groundRuleDTO;
        this.codeConventionDTO = codeConventionDTO;
        this.commitConventionDTO = commitConventionDTO;
    }
}
