package com.pcmk.dto.project;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmk.domain.project.ProjectEntity;
import com.pcmk.domain.project.codeconvention.CodeConvention;
import com.pcmk.domain.project.commitconvention.CommitConvention;
import com.pcmk.domain.project.groundrule.GroundRule;
import com.pcmk.domain.project.techstack.TechStack;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@Data
public class ProjectDTO {

    @JsonProperty("project_detail")
    private ProjectDetail projectDetail;

    @JsonProperty("tech_stack")
    private TechStack techStack;

    @JsonProperty("ground_rule")
    private GroundRule groundRule;

    @JsonProperty("code_convention")
    private CodeConvention codeConvention;

    @JsonProperty("commit_convention")
    private CommitConvention commitConvention;

    public static ProjectDTO fromEntity(ProjectEntity projectEntity) {
        return ProjectDTO.builder()
                .projectDetail(ProjectDetail.of(projectEntity))
                .techStack(projectEntity.getTechStack())
                .groundRule(projectEntity.getGroundRule())
                .commitConvention(projectEntity.getCommitConvention())
                .codeConvention(projectEntity.getCodeConvention())
                .build();
    }

    @Builder
    private ProjectDTO(ProjectDetail projectDetail,
                       CodeConvention codeConvention,
                       CommitConvention commitConvention,
                       GroundRule groundRule,
                       TechStack techStack) {
        this.projectDetail = projectDetail;
        this.codeConvention = codeConvention;
        this.commitConvention = commitConvention;
        this.groundRule = groundRule;
        this.techStack = techStack;
    }

    @NoArgsConstructor
    @Data
    public static class ProjectDetail {

        @JsonProperty("project_name")
        private String projectName;

        @JsonProperty("team_name")
        private String teamName;

        @JsonProperty("project_uuid")
        private String projectUUID;

        private String introduction;

        private String detail;

        public static ProjectDetail of(ProjectEntity projectEntity) {
            return ProjectDetail.builder()
                    .projectEntity(projectEntity)
                    .build();
        }

        @Builder
        private ProjectDetail(ProjectEntity projectEntity) {
            this.projectName = projectEntity.getProjectName();
            this.projectUUID = projectEntity.getProjectUUID();
            this.teamName = projectEntity.getTeamName();
            this.introduction = projectEntity.getIntroduction();
            this.detail = projectEntity.getDetail();
        }
    }
}
