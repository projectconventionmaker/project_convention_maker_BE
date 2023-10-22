package com.pcmk.infra.test;


import com.pcmk.domain.project.ProjectRepository;
import com.pcmk.infra.annotations.IntegrationTestSupport;
import com.pcmk.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTestSupport
public class AbstractIntegrationTest extends AbstractContainerBaseTest {

    @Autowired
    protected ProjectRepository projectRepository;

    @Autowired
    protected ProjectService projectService;

}
