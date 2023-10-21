package com.pcmk.service.project;

import static com.pcmk.exception.CustomExceptionError.PROJECT_NAME_DUPLICATED;
import static com.pcmk.exception.CustomExceptionError.PROJECT_NOT_FOUND;

import com.pcmk.domain.project.ProjectEntity;
import com.pcmk.domain.project.ProjectRepository;
import com.pcmk.domain.project.codeconvention.CodeConvention;
import com.pcmk.domain.project.commitconvention.CommitConvention;
import com.pcmk.domain.project.groundrule.GroundRule;
import com.pcmk.domain.project.techstack.TechStack;
import com.pcmk.dto.project.ProjectDTO;
import com.pcmk.dto.project.request.CodeConventionUpdateRequestDTO;
import com.pcmk.dto.project.request.CommitConventionUpdateRequestDTO;
import com.pcmk.dto.project.request.GroundRuleUpdateRequestDTO;
import com.pcmk.dto.project.request.ProjectUpdateRequestDTO;
import com.pcmk.dto.project.request.TechStackUpdateRequestDTO;
import com.pcmk.exception.CustomException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectDTO createProject(String projectName) {
        try {
            ensureProjectNameIsUnique(projectName);
            ProjectEntity save = projectRepository.save(buildProjectEntity(projectName));
            return ProjectDTO.fromEntity(save);
        } catch (DataIntegrityViolationException exception) {
            throw new CustomException(PROJECT_NAME_DUPLICATED);
        }
    }

    @Transactional
    public ProjectDTO updateProject(String projectUuid, ProjectUpdateRequestDTO projectUpdateRequestDTO) {
        ProjectEntity projectEntity = findOrException(projectUuid);
        if (!projectUpdateRequestDTO.getProjectName().equals(projectEntity.getProjectName())) {
            ensureProjectNameIsUnique(projectUpdateRequestDTO.getProjectName());
        }

        try {
            ProjectEntity updated = buildUpdatedProjectEntity(projectUpdateRequestDTO);
            projectEntity.updateProject(updated);
            projectRepository.save(projectEntity);
            return ProjectDTO.fromEntity(projectEntity);
        } catch (DataIntegrityViolationException exception) {
            throw new CustomException(PROJECT_NAME_DUPLICATED);
        }
    }

    @Transactional
    public ProjectDTO updateTechStack(String projectUuid, TechStackUpdateRequestDTO techStackUpdateRequestDTO) {
        ProjectEntity projectEntity = findOrException(projectUuid);
        TechStack updatedTechStack = TechStack.of(techStackUpdateRequestDTO.getLanguages(),
                techStackUpdateRequestDTO.getFrameworks(), techStackUpdateRequestDTO.getStyles());
        projectEntity.updateTechStack(updatedTechStack);
        projectRepository.save(projectEntity);
        return ProjectDTO.fromEntity(projectEntity);
    }

    @Transactional
    public ProjectDTO updateGroundRule(String projectUuid, GroundRuleUpdateRequestDTO groundRuleUpdateRequestDTO) {
        ProjectEntity projectEntity = findOrException(projectUuid);
        GroundRule updatedGroundRule = GroundRule.of(groundRuleUpdateRequestDTO.getItems());
        projectEntity.updateGroundRule(updatedGroundRule);
        projectRepository.save(projectEntity);
        return ProjectDTO.fromEntity(projectEntity);
    }

    @Transactional
    public ProjectDTO updateCommitConvention(String projectUuid,
                                             CommitConventionUpdateRequestDTO commitConventionUpdateRequestDTO) {
        ProjectEntity projectEntity = findOrException(projectUuid);
        CommitConvention updatedCommitConvention = CommitConvention.of(commitConventionUpdateRequestDTO.getItems());
        projectEntity.updateCommitConvention(updatedCommitConvention);
        projectRepository.save(projectEntity);
        return ProjectDTO.fromEntity(projectEntity);
    }

    @Transactional
    public ProjectDTO updateCodeConvention(String projectUuid,
                                           CodeConventionUpdateRequestDTO codeConventionUpdateRequestDTO) {
        ProjectEntity projectEntity = findOrException(projectUuid);
        CodeConvention updatedCodeConvention = CodeConvention.of(codeConventionUpdateRequestDTO.getItems());
        projectEntity.updateCodeConvention(updatedCodeConvention);
        projectRepository.save(projectEntity);
        return ProjectDTO.fromEntity(projectEntity);
    }

    private void ensureProjectNameIsUnique(String projectName) {
        if (projectRepository.existsByProjectName(projectName)) {
            throw new CustomException(PROJECT_NAME_DUPLICATED);
        }
    }

    private ProjectEntity findOrException(String projectUuid) {
        return projectRepository.findByProjectUUID(projectUuid)
                .orElseThrow(() -> new CustomException(PROJECT_NOT_FOUND));
    }

    private ProjectEntity buildProjectEntity(String projectName) {
        return ProjectEntity.builder()
                .projectName(projectName)
                .projectUUID(createUUID())
                .build();
    }

    private String createUUID() {
        return UUID.randomUUID().toString();
    }

    private static ProjectEntity buildUpdatedProjectEntity(ProjectUpdateRequestDTO dto) {
        return ProjectEntity.builder()
                .projectName(dto.getProjectName())
                .teamName(dto.getProjectName())
                .introduction(dto.getIntroduction())
                .detail(dto.getDetail())
                .build();
    }
}
