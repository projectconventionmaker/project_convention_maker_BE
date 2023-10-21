package com.pcmk.controller.project;

import com.pcmk.dto.project.ProjectDTO;
import com.pcmk.dto.project.request.ProjectCreateRequestDTO;
import com.pcmk.dto.project.request.TechStackUpdateRequestDTO;
import com.pcmk.service.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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

    @PutMapping("/{projectUuid}/tech-stack")
    public ProjectDTO updateTechStack(@PathVariable String projectUuid,
                                      @RequestBody @Validated TechStackUpdateRequestDTO techStackUpdateRequestDTO) {
        return projectService.updateTechStack(projectUuid, techStackUpdateRequestDTO);

    }


}
