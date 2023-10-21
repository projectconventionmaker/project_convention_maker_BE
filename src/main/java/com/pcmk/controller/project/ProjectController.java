package com.pcmk.controller.project;

import com.pcmk.dto.project.ProjectDTO;
import com.pcmk.dto.project.request.CodeConventionUpdateRequestDTO;
import com.pcmk.dto.project.request.CommitConventionUpdateRequestDTO;
import com.pcmk.dto.project.request.GroundRuleUpdateRequestDTO;
import com.pcmk.dto.project.request.ProjectCreateRequestDTO;
import com.pcmk.dto.project.request.ProjectUpdateRequestDTO;
import com.pcmk.dto.project.request.TechStackUpdateRequestDTO;
import com.pcmk.service.project.ProjectService;
import lombok.RequiredArgsConstructor;
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
    public ProjectDTO createProject(@RequestBody @Validated ProjectCreateRequestDTO projectCreateRequestDTO) {
        return projectService.createProject(projectCreateRequestDTO.getProjectName());
    }

    @GetMapping("/{projectUUID}")
    public ProjectDTO getProject(@PathVariable String projectUUID) {
        return projectService.getProject(projectUUID);
    }

    @PutMapping("/{projectUUID}")
    public ProjectDTO updateProject(@PathVariable String projectUUID,
                                    @RequestBody @Validated ProjectUpdateRequestDTO projectUpdateRequestDTO) {
        return projectService.updateProject(projectUUID, projectUpdateRequestDTO);
    }

    @PutMapping("/{projectUUID}/tech-stack")
    public ProjectDTO updateTechStack(@PathVariable String projectUUID,
                                      @RequestBody @Validated TechStackUpdateRequestDTO techStackUpdateRequestDTO) {
        return projectService.updateTechStack(projectUUID, techStackUpdateRequestDTO);
    }

    @PutMapping("/{projectUUID}/ground-rules")
    public ProjectDTO updateGroundRules(@PathVariable String projectUUID,
                                        @RequestBody @Validated GroundRuleUpdateRequestDTO groundRuleUpdateRequestDTO) {
        return projectService.updateGroundRule(projectUUID, groundRuleUpdateRequestDTO);
    }

    @PutMapping("/{projectUUID}/commit-conventions")
    public ProjectDTO updateCommitConventions(@PathVariable String projectUUID,
                                              @RequestBody @Validated CommitConventionUpdateRequestDTO commitConventionUpdateRequestDTO) {
        return projectService.updateCommitConvention(projectUUID, commitConventionUpdateRequestDTO);
    }

    @PutMapping("/{projectUUID}/code-conventions")
    public ProjectDTO updateCodeConventions(@PathVariable String projectUUID,
                                            @RequestBody @Validated CodeConventionUpdateRequestDTO codeConventionUpdateRequestDTO) {
        return projectService.updateCodeConvention(projectUUID, codeConventionUpdateRequestDTO);
    }
}
