package com.pcmk.service.project;

import static com.pcmk.exception.CustomExceptionError.PROJECT_NAME_DUPLICATED;
import static com.pcmk.exception.CustomExceptionError.PROJECT_NOT_FOUND;

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


    public ProjectGetResponseDTO getProject(String projectUuid) {
        ProjectEntity projectEntity = findOrException(projectUuid);
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
    public void updateProject(String projectUuid, ProjectUpdateRequestDTO dto) {
        ProjectEntity projectEntity = findOrException(projectUuid);

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
    public void updateTechStack(String projectUuid,
                                TechStackUpdateRequestDTO dto) {
        ProjectEntity projectEntity = findOrException(projectUuid);
        projectEntity.updateTechStack(dto.toEntity());
        projectRepository.save(projectEntity);
    }

    @Transactional
    public void updateGroundRule(String projectUuid, GroundRuleUpdateRequestDTO dto) {
        ProjectEntity projectEntity = findOrException(projectUuid);
        projectEntity.updateGroundRule(dto.toEntity());
        projectRepository.save(projectEntity);
    }

    @Transactional
    public void updateCommitConvention(String projectUuid,
                                       CommitConventionUpdateRequestDTO dto) {
        ProjectEntity projectEntity = findOrException(projectUuid);
        projectEntity.updateCommitConvention(dto.toEntity());
        projectRepository.save(projectEntity);
    }

    @Transactional
    public void updateCodeConvention(String projectUuid,
                                     CodeConventionUpdateRequestDTO dto) {
        ProjectEntity projectEntity = findOrException(projectUuid);
        projectEntity.updateCodeConvention(dto.toEntity());
        projectRepository.save(projectEntity);
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
}
