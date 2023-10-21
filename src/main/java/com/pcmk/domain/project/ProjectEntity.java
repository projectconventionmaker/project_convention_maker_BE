package com.pcmk.domain.project;

import com.pcmk.domain.BaseEntity;
import com.pcmk.domain.project.codeconvention.CodeConvention;
import com.pcmk.domain.project.commitconvention.CommitConvention;
import com.pcmk.domain.project.groundrule.GroundRule;
import com.pcmk.domain.project.techstack.TechStack;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "project")
@SQLDelete(sql = "UPDATE project SET deleted_at = NOW() WHERE id_comment = ?")
@Where(clause = "deleted_at is NULL")
@Entity
public class ProjectEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_project", nullable = false, updatable = false)
    private Long id;

    @Column(name = "project_name", unique = true, nullable = false, columnDefinition = "varchar(100) COMMENT '프로젝트 이름'")
    private String projectName;

    @Column(name = "team_name", columnDefinition = "varchar(100) COMMENT '팀 이름'")
    private String teamName;

    @Column(name = "introduction", columnDefinition = "varchar(255) COMMENT '프로젝트 한 줄 소개'")
    private String introduction;

    @Column(name = "project_uuid", unique = true, nullable = false, updatable = false, columnDefinition = "varchar(150) COMMENT '프로젝트 UUID'")
    private String projectUUID;

    @Column(name = "detail", columnDefinition = "text COMMENT '프로젝트 상세'")
    private String detail;

    @Type(JsonType.class)
    @Column(name = "tech_stack", columnDefinition = "json COMMENT '기술 스택'")
    private TechStack techStack;

    @Type(JsonType.class)
    @Column(name = "ground_rule", columnDefinition = "json COMMENT '그라운드 룰'")
    private GroundRule groundRule;

    @Type(JsonType.class)
    @Column(name = "commit_convention", columnDefinition = "json COMMENT '커밋 컨벤션'")
    private CommitConvention commitConvention;

    @Type(JsonType.class)
    @Column(name = "code_convention", columnDefinition = "json COMMENT '코드 컨벤션'")
    private CodeConvention codeConvention;

    @Builder
    private ProjectEntity(String projectName, String teamName, String introduction, String projectUUID, String detail) {
        this.projectName = projectName;
        this.teamName = teamName;
        this.introduction = introduction;
        this.detail = detail;
        this.projectUUID = projectUUID;
    }

    public void updateTechStack(TechStack updatedTechStack) {
        this.techStack = updatedTechStack;
    }

    public void updateGroundRule(GroundRule updatedGroundRule) {
        this.groundRule = updatedGroundRule;
    }

    public void updateCommitConvention(CommitConvention updatedCommitConvention) {
        this.commitConvention = updatedCommitConvention;
    }

    public void updateCodeConvention(CodeConvention updatedCodeConvention) {
        this.codeConvention = updatedCodeConvention;
    }

    public void updateProject(ProjectEntity updatedProjectEntity) {
        this.projectName = updatedProjectEntity.getProjectName();
        this.teamName = updatedProjectEntity.getTeamName();
        this.introduction = updatedProjectEntity.getIntroduction();
        this.detail = updatedProjectEntity.getDetail();
    }
}
