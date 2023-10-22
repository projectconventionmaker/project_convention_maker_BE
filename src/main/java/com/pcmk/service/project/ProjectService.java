package com.pcmk.service.project;

import static com.pcmk.exception.CustomExceptionError.PROJECT_NAME_DUPLICATED;
import static com.pcmk.exception.CustomExceptionError.PROJECT_NOT_FOUND;
import static com.pcmk.utils.UUIdUtils.createUUID;
import static com.pcmk.utils.UUIdUtils.isUUIDFormat;

import com.pcmk.domain.project.ProjectEntity;
import com.pcmk.domain.project.ProjectRepository;
import com.pcmk.dto.project.codeconvention.CodeConventionUpdateRequestDTO;
import com.pcmk.dto.project.commitconvention.CommitConventionUpdateRequestDTO;
import com.pcmk.dto.project.groundrule.GroundRuleUpdateRequestDTO;
import com.pcmk.dto.project.project.ProjectCreateResponseDTO;
import com.pcmk.dto.project.project.ProjectGetResponseDTO;
import com.pcmk.dto.project.project.ProjectUpdateRequestDTO;
import com.pcmk.dto.project.techstack.TechStackUpdateRequestDTO;
import com.pcmk.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    
    public ProjectGetResponseDTO getProject(String projectId) {
        ProjectEntity projectEntity = findOrException(projectId);
        return ProjectGetResponseDTO.fromEntity(projectEntity);
    }

    @Transactional
    public ProjectCreateResponseDTO createProject(String projectName) {
        try {
            ensureProjectNameIsUnique(projectName);
            ProjectEntity save = projectRepository.save(buildProjectEntity(projectName));
            return ProjectCreateResponseDTO.fromEntity(save);
        } catch (DataIntegrityViolationException exception) {
            throw new CustomException(PROJECT_NAME_DUPLICATED);
        }
    }

    @Transactional
    public void updateProject(String projectId, ProjectUpdateRequestDTO dto) {
        ProjectEntity projectEntity = findOrException(projectId);

        if (!dto.getProjectName().equals(projectEntity.getProjectName())) {
            ensureProjectNameIsUnique(dto.getProjectName());
        }

        try {
            projectEntity.updateProject(dto.toEntity());
            projectRepository.save(projectEntity);
        } catch (DataIntegrityViolationException exception) {
            throw new CustomException(PROJECT_NAME_DUPLICATED);
        }
    }

    @Transactional
    public void updateTechStack(String projectId,
                                TechStackUpdateRequestDTO dto) {
        ProjectEntity projectEntity = findOrException(projectId);
        projectEntity.updateTechStack(dto.toEntity());
        projectRepository.save(projectEntity);
    }

    @Transactional
    public void updateGroundRule(String projectId, GroundRuleUpdateRequestDTO dto) {
        ProjectEntity projectEntity = findOrException(projectId);
        projectEntity.updateGroundRule(dto.toEntity());
        projectRepository.save(projectEntity);
    }

    @Transactional
    public void updateCommitConvention(String projectId,
                                       CommitConventionUpdateRequestDTO dto) {
        ProjectEntity projectEntity = findOrException(projectId);
        projectEntity.updateCommitConvention(dto.toEntity());
        projectRepository.save(projectEntity);
    }

    @Transactional
    public void updateCodeConvention(String projectId,
                                     CodeConventionUpdateRequestDTO dto) {
        ProjectEntity projectEntity = findOrException(projectId);
        projectEntity.updateCodeConvention(dto.toEntity());
        projectRepository.save(projectEntity);
    }

    private void ensureProjectNameIsUnique(String projectName) {
        if (projectRepository.existsByProjectName(projectName)) {
            throw new CustomException(PROJECT_NAME_DUPLICATED);
        }
    }

    private ProjectEntity findOrException(String projectId) {
        if (isUUIDFormat(projectId)) {
            return projectRepository.findByProjectUUID(projectId)
                    .orElseThrow(() -> new CustomException(PROJECT_NOT_FOUND));
        }

        return projectRepository.findByProjectName(projectId)
                .orElseThrow(() -> new CustomException(PROJECT_NOT_FOUND));
    }

    private ProjectEntity buildProjectEntity(String projectName) {
        return ProjectEntity.builder()
                .projectName(projectName)
                .projectUUID(createUUID())
                .build();
    }
}
