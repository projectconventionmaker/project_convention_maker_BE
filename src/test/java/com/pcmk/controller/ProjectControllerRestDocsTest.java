package com.pcmk.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.pcmk.controller.project.ProjectController;
import com.pcmk.dto.project.ProjectDTO;
import com.pcmk.dto.project.request.ProjectCreateRequestDTO;
import com.pcmk.infra.test.AbstractRestDocsTest;
import com.pcmk.service.project.ProjectService;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.restdocs.payload.FieldDescriptor;

@DisplayName("프로젝트 API 문서화")
class ProjectControllerRestDocsTest extends AbstractRestDocsTest {

    private final ProjectService projectService = mock(ProjectService.class);

    @Override
    protected ProjectController initController() {
        return new ProjectController(projectService);
    }

    @Test
    @DisplayName("등록: 프로젝트 생성")
    void createProject_restDoc() throws Exception {

        ProjectCreateRequestDTO request = ProjectCreateRequestDTO.builder()
                .projectName("Boiler Plate")
                .build();

        ProjectDTO response = ProjectDTO.builder()
                .projectName(request.getProjectName())
                .projectUuid(UUID.randomUUID().toString())
                .build();

        BDDMockito.given(projectService.createProject(request.getProjectName()))
                .willReturn(response);

        var requestFieldDescription = new FieldDescriptor[]{
                fieldWithPath("project_name").type(STRING).description("(Required) 프로젝트 이름")
        };

        var responseFieldDescription = new FieldDescriptor[]{
                fieldWithPath("project_name").type(STRING).description("프로젝트 이름"),
                fieldWithPath("project_uuid").type(STRING).description("프로젝트 UUID")
        };

        //when //then
        mockMvc.perform(
                        post("/api/v1/projects")
                                .contentType(APPLICATION_JSON)
                                .content(body(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-v1-create-project",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andDo(
                        MockMvcRestDocumentationWrapper.document(
                                "post-v1-create-project",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Project")
                                        .description("프로젝트 생성 API")
                                        .requestFields(requestFieldDescription)
                                        .responseFields(responseFieldDescription)
                                        .build())));
    }

}
