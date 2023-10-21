package com.pcmk.service.project;

import com.pcmk.domain.project.ProjectEntity;
import com.pcmk.domain.project.ProjectRepository;
import com.pcmk.domain.project.groundrule.GroundRule;
import com.pcmk.domain.project.techstack.TechStack;
import com.pcmk.dto.project.ProjectDTO;
import com.pcmk.dto.project.request.GroundRuleUpdateRequestDTO;
import com.pcmk.dto.project.request.TechStackUpdateRequestDTO;
import com.pcmk.exception.CustomException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectDTO createProject(String projectName) {
        ensureProjectNameIsUnique(projectName);
        ProjectEntity save = projectRepository.save(buildProjectEntity(projectName));
        return ProjectDTO.fromEntity(save);
    }

    @Transactional
    public ProjectDTO updateTechStack(String projectUuid, TechStackUpdateRequestDTO techStackUpdateRequestDTO) {
        ProjectEntity projectEntity = projectRepository.findByProjectUuid(projectUuid)
                .orElseThrow(() -> new CustomException());
        TechStack updatedTechStack = buildUpdatedTechStack(techStackUpdateRequestDTO);
        projectEntity.updateTechStack(updatedTechStack);
        return ProjectDTO.fromEntity(projectEntity);
    }

    @Transactional
    public ProjectDTO updateGroundRule(String projectUuid, GroundRuleUpdateRequestDTO groundRuleUpdateRequestDTO) {
        ProjectEntity projectEntity = projectRepository.findByProjectUuid(projectUuid)
                .orElseThrow(() -> new CustomException());
        GroundRule groundRule = buildUpdatedGroundRule(groundRuleUpdateRequestDTO);
        projectEntity.updateGroundRule(groundRule);
        return ProjectDTO.fromEntity(projectEntity);
    }

    private TechStack buildUpdatedTechStack(TechStackUpdateRequestDTO techStackUpdateRequestDTO) {
        return TechStack.builder()
                .languages(techStackUpdateRequestDTO.getLanguages())
                .frameworks(techStackUpdateRequestDTO.getFrameworks())
                .styles(techStackUpdateRequestDTO.getStyles())
                .build();
    }

    private GroundRule buildUpdatedGroundRule(GroundRuleUpdateRequestDTO groundRuleUpdateRequestDTO) {
        return GroundRule.builder()
                .items(groundRuleUpdateRequestDTO.getItems())
                .build();
    }

    private void ensureProjectNameIsUnique(String projectName) {
        if (projectRepository.existsByProjectName(projectName)) {
            throw new CustomException();
        }
    }

    private ProjectEntity buildProjectEntity(String projectName) {
        return ProjectEntity.builder()
                .projectName(projectName)
                .projectUuid(createUUID())
                .build();
    }

    private String createUUID() {
        return UUID.randomUUID().toString();
    }
}
