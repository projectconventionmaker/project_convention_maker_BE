package com.pcmk.controller.project;

import com.pcmk.dto.project.codeconvention.CodeConventionUpdateRequestDTO;
import com.pcmk.dto.project.commitconvention.CommitConventionUpdateRequestDTO;
import com.pcmk.dto.project.groundrule.GroundRuleUpdateRequestDTO;
import com.pcmk.dto.project.project.ProjectCreateRequestDTO;
import com.pcmk.dto.project.project.ProjectCreateResponseDTO;
import com.pcmk.dto.project.project.ProjectGetResponseDTO;
import com.pcmk.dto.project.project.ProjectUpdateRequestDTO;
import com.pcmk.dto.project.techstack.TechStackUpdateRequestDTO;
import com.pcmk.service.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ProjectCreateResponseDTO createProject(
            @RequestBody @Validated ProjectCreateRequestDTO projectCreateRequestDTO) {
        return projectService.createProject(projectCreateRequestDTO.getProjectName());
    }

    @GetMapping("/{projectId}")
    public ProjectGetResponseDTO getProject(@PathVariable String projectId) {
        return projectService.getProject(projectId);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Void> updateProject(@PathVariable String projectId,
                                              @RequestBody @Validated ProjectUpdateRequestDTO projectUpdateRequestDTO) {
        projectService.updateProject(projectId, projectUpdateRequestDTO);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{projectId}/tech-stack")
    public ResponseEntity<Void> updateTechStack(@PathVariable String projectId,
                                                @RequestBody @Validated TechStackUpdateRequestDTO techStackUpdateRequestDTO) {
        projectService.updateTechStack(projectId, techStackUpdateRequestDTO);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{projectId}/ground-rules")
    public ResponseEntity<Void> updateGroundRules(@PathVariable String projectId,
                                                  @RequestBody @Validated GroundRuleUpdateRequestDTO groundRuleUpdateRequestDTO) {
        projectService.updateGroundRule(projectId, groundRuleUpdateRequestDTO);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{projectId}/commit-conventions")
    public ResponseEntity<Void> updateCommitConventions(@PathVariable String projectId,
                                                        @RequestBody @Validated CommitConventionUpdateRequestDTO commitConventionUpdateRequestDTO) {
        projectService.updateCommitConvention(projectId, commitConventionUpdateRequestDTO);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{projectId}/code-conventions")
    public ResponseEntity<Void> updateCodeConventions(@PathVariable String projectId,
                                                      @RequestBody @Validated CodeConventionUpdateRequestDTO codeConventionUpdateRequestDTO) {
        projectService.updateCodeConvention(projectId, codeConventionUpdateRequestDTO);
        return ResponseEntity.ok(null);
    }
}
