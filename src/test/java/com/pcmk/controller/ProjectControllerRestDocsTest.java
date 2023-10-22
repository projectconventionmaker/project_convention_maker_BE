package com.pcmk.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NULL;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.pcmk.controller.project.ProjectController;
import com.pcmk.domain.project.ProjectEntity;
import com.pcmk.domain.project.teammate.Teammate;
import com.pcmk.domain.project.teammate.TeammateElement;
import com.pcmk.dto.project.codeconvention.CodeConventionDTO;
import com.pcmk.dto.project.codeconvention.CodeConventionElementDTO;
import com.pcmk.dto.project.codeconvention.CodeConventionElementItemDTO;
import com.pcmk.dto.project.codeconvention.CodeConventionUpdateRequestDTO;
import com.pcmk.dto.project.commitconvention.CommitConventionDTO;
import com.pcmk.dto.project.commitconvention.CommitConventionElementDTO;
import com.pcmk.dto.project.commitconvention.CommitConventionUpdateRequestDTO;
import com.pcmk.dto.project.groundrule.GroundRuleDTO;
import com.pcmk.dto.project.groundrule.GroundRuleElementDTO;
import com.pcmk.dto.project.groundrule.GroundRuleUpdateRequestDTO;
import com.pcmk.dto.project.project.ProjectCreateRequestDTO;
import com.pcmk.dto.project.project.ProjectCreateResponseDTO;
import com.pcmk.dto.project.project.ProjectDetailDTO;
import com.pcmk.dto.project.project.ProjectGetResponseDTO;
import com.pcmk.dto.project.project.ProjectUpdateRequestDTO;
import com.pcmk.dto.project.teammate.TeammateElementDTO;
import com.pcmk.dto.project.techstack.TechStackDTO;
import com.pcmk.dto.project.techstack.TechStackElementDTO;
import com.pcmk.dto.project.techstack.TechStackUpdateRequestDTO;
import com.pcmk.infra.test.AbstractRestDocsTest;
import com.pcmk.service.project.ProjectService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;

@DisplayName("프로젝트 API 문서화")
class ProjectControllerRestDocsTest extends AbstractRestDocsTest {

    public static final String PROJECT_TAG = "Projects";
    public static final String PROJECT_NAME = "Boiler Plate";
    public static final String PROJECT_UUID = UUID.randomUUID().toString();
    private final ProjectService projectService = mock(ProjectService.class);

    @Override
    protected ProjectController initController() {
        return new ProjectController(projectService);
    }

    @Test
    @DisplayName("등록: 프로젝트 생성")
    void createProject_restDoc() throws Exception {

        ProjectCreateRequestDTO request = ProjectCreateRequestDTO.builder()
                .projectName(PROJECT_NAME)
                .build();

        ProjectCreateResponseDTO response = ProjectCreateResponseDTO.fromEntity(ProjectEntity.builder()
                .projectName(PROJECT_NAME)
                .projectUUID(PROJECT_UUID)
                .build());

        BDDMockito.given(projectService.createProject(request.getProjectName()))
                .willReturn(response);

        var requestFieldDescription = new FieldDescriptor[]{
                fieldWithPath("project_name").type(STRING).description("(Required) 프로젝트명")
        };

        var responseFieldDescription = new FieldDescriptor[]{
                fieldWithPath("project_detail").type(OBJECT).description("이름"),
                fieldWithPath("project_detail.project_name").type(STRING).description("프로젝트명"),
                fieldWithPath("project_detail.project_uuid").type(STRING).description("프로젝트 UUID"),
                fieldWithPath("project_detail.team_name").type(NULL).description("(Nullable) 팀명"),
                fieldWithPath("project_detail.introduction").type(NULL).description("(Nullable) 프로젝트 한 줄 소개"),
                fieldWithPath("project_detail.detail").type(NULL).description("(Nullable)  프로젝트 상세"),
                fieldWithPath("project_detail.project_start").type(NULL).description("(Nullable) 프로젝트 시작 일자"),
                fieldWithPath("project_detail.project_end").type(NULL).description("(Nullable) 프로젝트 종료 일자"),
                fieldWithPath("project_detail.teammates").type(NULL).description("(Nullable)  프로젝트 팀원")
        };

        String identifier = "post-v1-create-project";
        String summary = "프로젝트 생성 API";
        String description = """
                - 필수값 비어있는 경우 400 에러 코드
                - 이미 존재하는 프로젝트 이름 존재시 1001 에러 코드
                """;

        //when //then
        mockMvc.perform(
                        post("/api/v1/projects")
                                .contentType(APPLICATION_JSON)
                                .content(body(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(identifier,
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andDo(
                        MockMvcRestDocumentationWrapper.document(
                                identifier,
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag(PROJECT_TAG)
                                        .description(description)
                                        .summary(summary)
                                        .requestSchema(Schema.schema("CreateProjectRequest"))
                                        .responseSchema(Schema.schema("CreateProjectResponse"))
                                        .requestFields(requestFieldDescription)
                                        .responseFields(responseFieldDescription)
                                        .build())));
    }

    @Test
    @DisplayName("조회: 프로젝트 조회")
    void getProject_restDocs() throws Exception {
        //given

        //Teammate
        List<TeammateElement> teammateElements = new ArrayList<>();
        teammateElements.add(TeammateElement.of("name1", "백엔드", "https://www.link.com"));
        teammateElements.add(TeammateElement.of("name2", "백엔드", "https://www.link.com"));
        teammateElements.add(TeammateElement.of("name3", "백엔드", "https://www.link.com"));
        Teammate teammate = Teammate.of(teammateElements);

        //ProjectDetail
        ProjectDetailDTO projectDetail = ProjectDetailDTO.fromEntity(ProjectEntity.builder()
                .projectName(PROJECT_NAME)
                .projectUUID(PROJECT_UUID)
                .teamName("Boiler Plate")
                .introduction("프로젝트 한 줄 소개")
                .detail("프로젝트 상세 정보")
                .startAt(LocalDate.now())
                .endAt(LocalDate.now().plusDays(5))
                .teammate(teammate)
                .build());

        //TechStack
        List<TechStackElementDTO> techStackElements = new ArrayList<>();
        techStackElements.add(TechStackElementDTO.of("Language", List.of("Java", "Kotlin", "Golang")));
        techStackElements.add(
                TechStackElementDTO.of("DB", List.of("MySQL", "MariaDB", "DynamoDB", "MongoDB", "Redis")));
        techStackElements.add(TechStackElementDTO.of("Framework", List.of("Spring", "FastAPI", "Django")));
        TechStackDTO techStackDTO = TechStackDTO.of(techStackElements);

        //GroundRule
        List<GroundRuleElementDTO> groundRuleElements = new ArrayList<>();
        groundRuleElements.add(GroundRuleElementDTO.of("님 금지! 극 존대 금지!", true));
        groundRuleElements.add(GroundRuleElementDTO.of("ZEP에서 캠은 켜지 않을 거에요", false));
        groundRuleElements.add(GroundRuleElementDTO.of("스프린트는 협업과 프로세스를 배우는 시간이지 포트폴리오나 사이드 프로젝트를 하는 시간이 아닙니다", true));
        GroundRuleDTO groundRuleDTO = GroundRuleDTO.of(groundRuleElements);

        //CodeConvention
        List<CodeConventionElementItemDTO> codeConventionItems = new ArrayList<>();
        codeConventionItems.add(CodeConventionElementItemDTO.of("클래스명은 카멜케이스로 만든다", "CamelCase"));
        codeConventionItems.add(CodeConventionElementItemDTO.of("메서드명은 동사로 시작한다", "updateCodeConvention"));
        List<CodeConventionElementDTO> codeConventionElements = List.of(
                CodeConventionElementDTO.of("자바 네이밍", codeConventionItems));
        CodeConventionDTO codeConventionDTO = CodeConventionDTO.of(codeConventionElements);

        //CommitConvention
        List<CommitConventionElementDTO> commitElements = new ArrayList<>();
        commitElements.add(CommitConventionElementDTO.of("[feat]: 기능 추가,삭제, 변경", true));
        commitElements.add(CommitConventionElementDTO.of("[fix]: 버그,오류 수정", true));
        commitElements.add(CommitConventionElementDTO.of("[refactor]: 코드 리팩토링:", true));
        CommitConventionDTO commitConventionDTO = CommitConventionDTO.of(commitElements);

        ProjectGetResponseDTO response = ProjectGetResponseDTO.builder()
                .projectDetailDTO(projectDetail)
                .techStackDTO(techStackDTO)
                .groundRuleDTO(groundRuleDTO)
                .codeConventionDTO(codeConventionDTO)
                .commitConventionDTO(commitConventionDTO)
                .build();

        BDDMockito.given(projectService.getProject(PROJECT_UUID))
                .willReturn(response);

        var parameterDescriptors = new ParameterDescriptor[]{
                parameterWithName("projectId").description("(Required) 프로젝트명 or 프로젝트 UUID")
        };

        String identifier = "get-v1-get-project";
        String summary = "프로젝트 조회 API";
        String description = """
                - 존재하지 않는 프로젝트 UUID로 요청시 1000 에러 코드
                """;

        var responseFieldDescription = new FieldDescriptor[]{
                fieldWithPath("project_detail").type(OBJECT).description("프로젝트 개요"),
                fieldWithPath("project_detail.project_name").type(STRING).description("프로젝트명"),
                fieldWithPath("project_detail.project_uuid").type(STRING).description("프로젝트명 UUID"),
                fieldWithPath("project_detail.team_name").type(STRING).description("(Nullable) 팀명"),
                fieldWithPath("project_detail.introduction").type(STRING).description("(Nullable) 프로젝트 한 줄 소개"),
                fieldWithPath("project_detail.detail").type(STRING).description("(Nullable) 프로젝트 상세"),
                fieldWithPath("project_detail.project_start").type(STRING).description("(Nullable) 프로젝트 시작 일자"),
                fieldWithPath("project_detail.project_end").type(STRING).description("(Nullable) 프로젝트 종료 일자"),
                fieldWithPath("project_detail.teammates").type(ARRAY).description("(Nullable) 프로젝트 팀원"),
                fieldWithPath("project_detail.teammates[].name").type(STRING).description("(Nullable) 팀원명"),
                fieldWithPath("project_detail.teammates[].position").type(STRING).description("(Nullable) 팀원 포지션"),
                fieldWithPath("project_detail.teammates[].link").type(STRING).description("(Nullable) 팀원 링크"),

                fieldWithPath("tech_stack").type(OBJECT).description("(Nullable) 테크 스택"),
                fieldWithPath("tech_stack.elements").type(ARRAY).description("(Nullable) 스택 리스트"),
                fieldWithPath("tech_stack.elements[].category").type(STRING).description("(Nullable) 기술 카테고리"),
                fieldWithPath("tech_stack.elements[].names").type(ARRAY).description("(Nullable) 기술 이름"),

                fieldWithPath("ground_rule").type(OBJECT).description("(Nullable) 그라운드 룰"),
                fieldWithPath("ground_rule.elements").type(ARRAY).description("(Nullable) 그라운드 룰 항목"),
                fieldWithPath("ground_rule.elements[].name").type(STRING).description("(Nullable) 항목 이름"),
                fieldWithPath("ground_rule.elements[].checked").type(BOOLEAN).description("(Nullable) 항목이 채택되었는지"),

                fieldWithPath("commit_convention").type(OBJECT).description("(Nullable) 커밋 컨벤션"),
                fieldWithPath("commit_convention.elements").type(ARRAY).description("(Nullable) 커밋 컨벤션 항목"),
                fieldWithPath("commit_convention.elements[].name").type(STRING).description("(Nullable) 항목 이름"),
                fieldWithPath("commit_convention.elements[].checked").type(BOOLEAN).description(
                        "(Nullable) 항목이 채택되었는지"),

                fieldWithPath("code_convention").type(OBJECT).description("(Nullable) 코드 컨벤션"),
                fieldWithPath("code_convention.elements").type(ARRAY).description("(Nullable) 코드 컨벤션"),
                fieldWithPath("code_convention.elements[].category").type(STRING).description("(Nullable) 카테고리명"),
                fieldWithPath("code_convention.elements[].items").type(ARRAY).description("(Nullable) 컨벤션 항목"),
                fieldWithPath("code_convention.elements[].items[].name").type(STRING).description("(Nullable) 항목명"),
                fieldWithPath("code_convention.elements[].items[].example").type(STRING).description("(Nullable) 예시")
        };

        //when //then
        mockMvc.perform(
                        get("/api/v1/projects/{projectId}", PROJECT_UUID))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(identifier,
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andDo(
                        MockMvcRestDocumentationWrapper.document(
                                identifier,
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag(PROJECT_TAG)
                                        .description(description)
                                        .summary(summary)
                                        .requestSchema(Schema.schema("GetProjectRequest"))
                                        .responseSchema(Schema.schema("GetProjectResponse"))
                                        .pathParameters(parameterDescriptors)
                                        .responseFields(responseFieldDescription)
                                        .build())));
    }

    @Test
    @DisplayName("수정: 프로젝트 개요")
    void updateProject_restDocs() throws Exception {
        //given
        //Teammate
        List<TeammateElementDTO> teammateElementDTOs = new ArrayList<>();
        teammateElementDTOs.add(TeammateElementDTO.of("name1", "백엔드", "https://www.link.com"));
        teammateElementDTOs.add(TeammateElementDTO.of("name2", "프론트엔드", "https://www.link.com"));
        teammateElementDTOs.add(TeammateElementDTO.of("name3", "디자이너", "https://www.link.com"));

        ProjectUpdateRequestDTO request = ProjectUpdateRequestDTO.builder()
                .projectName("Changed project name")
                .teamName("Changed team name")
                .introduction("Changed project introduction")
                .detail("Change project detail")
                .teammateElementDTOs(teammateElementDTOs)
                .startAt(LocalDate.now())
                .endAt(LocalDate.now().plusDays(5))
                .build();

        var parameterDescriptors = new ParameterDescriptor[]{
                parameterWithName("projectId").description("(Required) 프로젝트명 or 프로젝트 UUID")
        };

        String identifier = "put-v1-update-project";
        String summary = "프로젝트 개요 수정 API";
        String description = """
                - 필수값 비어있는 경우 400 에러 코드
                - 존재하지 않는 프로젝트명, UUID로 요청시 1000 에러 코드
                - 변경된 프로젝트명이 사용중인 경우 1001 에러 코드
                """;

        //when //then
        mockMvc.perform(
                        put("/api/v1/projects/{projectId}", PROJECT_UUID)
                                .contentType(APPLICATION_JSON)
                                .content(body(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(identifier,
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andDo(
                        MockMvcRestDocumentationWrapper.document(
                                identifier,
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag(PROJECT_TAG)
                                        .description(description)
                                        .summary(summary)
                                        .requestSchema(Schema.schema("UpdateProjectRequest"))
                                        .pathParameters(parameterDescriptors)
                                        .build())));
    }

    @Test
    @DisplayName("수정: 프로젝트 기술스택")
    void updateTechStack_restDocs() throws Exception {
        //given
        List<TechStackElementDTO> elementDTOs = new ArrayList<>();
        elementDTOs.add(TechStackElementDTO.of("Language", List.of("Java", "Kotlin", "Golang")));
        elementDTOs.add(TechStackElementDTO.of("DB", List.of("MySQL", "MariaDB", "DynamoDB", "MongoDB", "Redis")));
        elementDTOs.add(TechStackElementDTO.of("Framework", List.of("Spring", "FastAPI", "Django")));
        TechStackUpdateRequestDTO request = TechStackUpdateRequestDTO.of(elementDTOs);

        //given restdocs
        var parameterDescriptors = new ParameterDescriptor[]{
                parameterWithName("projectId").description("(Required) 프로젝트명 or 프로젝트 UUID")
        };

        var requestFieldDescription = new FieldDescriptor[]{
                fieldWithPath("tech_stack").type(ARRAY).description("(Required) 테크 스택 리스트"),
                fieldWithPath("tech_stack[].category").type(STRING).description("(Required) 스택 카테고리"),
                fieldWithPath("tech_stack[].names").type(ARRAY).description("(Required) 기술 이름")
        };

        String identifier = "put-v1-update-project-tech-stack";
        String summary = "프로젝트 기술 스택 수정 API";
        String description = """
                - 필수값 비어있는 경우 400 에러 코드
                - 존재하지 않는 프로젝트 UUID로 요청시 1000 에러 코드
                """;

        //when //then
        mockMvc.perform(
                        put("/api/v1/projects/{projectId}/tech-stack", PROJECT_UUID)
                                .contentType(APPLICATION_JSON)
                                .content(body(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(identifier,
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andDo(
                        MockMvcRestDocumentationWrapper.document(
                                identifier,
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag(PROJECT_TAG)
                                        .description(description)
                                        .summary(summary)
                                        .requestSchema(Schema.schema("UpdateTechStackRequest"))
                                        .pathParameters(parameterDescriptors)
                                        .requestFields(requestFieldDescription)
                                        .build())));
    }

    @Test
    @DisplayName("수정: 프로젝트 그라운드 룰")
    void updateGroundRules_restDocs() throws Exception {
        //given
        List<GroundRuleElementDTO> elementDTOs = new ArrayList<>();
        elementDTOs.add(GroundRuleElementDTO.of("님 금지! 극 존대 금지!", true));
        elementDTOs.add(GroundRuleElementDTO.of("ZEP에서 캠은 켜지 않을 거에요", false));
        elementDTOs.add(GroundRuleElementDTO.of("스프린트는 협업과 프로세스를 배우는 시간이지 포트폴리오나 사이드 프로젝트를 하는 시간이 아닙니다", true));
        GroundRuleUpdateRequestDTO request = GroundRuleUpdateRequestDTO.of(elementDTOs);

        var parameterDescriptors = new ParameterDescriptor[]{
                parameterWithName("projectId").description("(Required) 프로젝트명 or 프로젝트 UUID")
        };

        var requestFieldDescription = new FieldDescriptor[]{
                fieldWithPath("ground_rules").type(ARRAY).description("(Required) 그라운드 룰 항목"),
                fieldWithPath("ground_rules[].name").type(STRING).description("(Required) 항목 이름"),
                fieldWithPath("ground_rules[].checked").type(BOOLEAN).description("(Required) 항목이 채택되었는지")
        };

        String identifier = "put-v1-update-project-ground-rule";
        String summary = "프로젝트 그라운드 룰 수정 API";
        String description = """
                - 필수값 비어있는 경우 400 에러 코드
                - 존재하지 않는 프로젝트 UUID로 요청시 1000 에러 코드
                """;

        //when //then
        mockMvc.perform(
                        put("/api/v1/projects/{projectId}/ground-rules", PROJECT_UUID)
                                .contentType(APPLICATION_JSON)
                                .content(body(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(identifier,
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andDo(
                        MockMvcRestDocumentationWrapper.document(
                                identifier,
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag(PROJECT_TAG)
                                        .description(description)
                                        .summary(summary)
                                        .requestSchema(Schema.schema("UpdateGroundRuleRequest"))
                                        .pathParameters(parameterDescriptors)
                                        .requestFields(requestFieldDescription)
                                        .build())));
    }

    @Test
    @DisplayName("수정: 프로젝트 커밋 컨벤션")
    void updateCommitConventions_restDocs() throws Exception {
        //given
        List<CommitConventionElementDTO> elementDTOs = new ArrayList<>();
        elementDTOs.add(CommitConventionElementDTO.of("[feat]: 기능 추가,삭제, 변경", true));
        elementDTOs.add(CommitConventionElementDTO.of("[fix]: 버그,오류 수정", true));
        elementDTOs.add(CommitConventionElementDTO.of("[refactor]: 코드 리팩토링:", true));
        CommitConventionUpdateRequestDTO request = CommitConventionUpdateRequestDTO.of(elementDTOs);

        var parameterDescriptors = new ParameterDescriptor[]{
                parameterWithName("projectId").description("(Required) 프로젝트명 or 프로젝트 UUID")
        };

        var requestFieldDescription = new FieldDescriptor[]{
                fieldWithPath("code_conventions").type(ARRAY).description("(Required) 커밋 컨벤션 항목"),
                fieldWithPath("code_conventions[].name").type(STRING).description("(Required) 항목 이름"),
                fieldWithPath("code_conventions[].checked").type(BOOLEAN).description("(Required) 항목이 채택되었는지")
        };

        String identifier = "put-v1-update-project-commit-conventions";
        String summary = "프로젝트 커밋 컨벤션 수정 API";
        String description = """
                - 필수값 비어있는 경우 400 에러 코드
                - 존재하지 않는 프로젝트 UUID로 요청시 1000 에러 코드
                """;

        //when //then
        mockMvc.perform(
                        put("/api/v1/projects/{projectId}/commit-conventions", PROJECT_UUID)
                                .contentType(APPLICATION_JSON)
                                .content(body(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(identifier,
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andDo(
                        MockMvcRestDocumentationWrapper.document(
                                identifier,
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag(PROJECT_TAG)
                                        .description(description)
                                        .summary(summary)
                                        .requestSchema(Schema.schema("UpdateCommitConventionRequest"))
                                        .pathParameters(parameterDescriptors)
                                        .requestFields(requestFieldDescription)
                                        .build())));
    }

    @Test
    @DisplayName("수정: 프로젝트 코드 컨벤션")
    void updateCodeConventions_restDocs() throws Exception {
        //given
        List<CodeConventionElementItemDTO> example1 = new ArrayList<>();
        example1.add(CodeConventionElementItemDTO.of("클래스명은 카멜케이스로 만든다", "CamelCase"));
        example1.add(CodeConventionElementItemDTO.of("메서드명은 동사로 시작한다", "updateCodeConvention"));

        List<CodeConventionElementItemDTO> example2 = new ArrayList<>();
        example2.add(CodeConventionElementItemDTO.of("테스트명은 메서드명_테스트상태_기대행위", "updateConvention_withValidInput_pass"));
        example2.add(CodeConventionElementItemDTO.of("변수명은 lower camel case", "codeConvention"));

        List<CodeConventionElementDTO> elementDTOs = new ArrayList<>();
        elementDTOs.add(CodeConventionElementDTO.of("자바 네이밍", example1));
        elementDTOs.add(CodeConventionElementDTO.of("코틀린 네이밍", example2));

        CodeConventionUpdateRequestDTO request = CodeConventionUpdateRequestDTO.of(elementDTOs);

        var parameterDescriptors = new ParameterDescriptor[]{
                parameterWithName("projectId").description("(Required) 프로젝트명 or 프로젝트 UUID")
        };

        var requestFieldDescription = new FieldDescriptor[]{
                fieldWithPath("code_conventions").type(ARRAY).description("(Required) 코드 컨벤션"),
                fieldWithPath("code_conventions[].category").type(STRING).description("(Required) 카테고리명"),
                fieldWithPath("code_conventions[].items").type(ARRAY).description("(Required) 컨벤션 항목"),
                fieldWithPath("code_conventions[].items[].name").type(STRING).description("(Required) 항목명"),
                fieldWithPath("code_conventions[].items[].example").type(STRING).description("(Required) 예시")
        };

        String identifier = "put-v1-update-project-code-conventions";
        String summary = "프로젝트 코드 컨벤션 수정 API";
        String description = """
                - 필수값 비어있는 경우 400 에러 코드
                - 존재하지 않는 프로젝트 UUID로 요청시 1000 에러 코드
                """;

        //when //then
        mockMvc.perform(
                        put("/api/v1/projects/{projectId}/code-conventions", PROJECT_UUID)
                                .contentType(APPLICATION_JSON)
                                .content(body(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(identifier,
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andDo(
                        MockMvcRestDocumentationWrapper.document(
                                identifier,
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag(PROJECT_TAG)
                                        .description(description)
                                        .summary(summary)
                                        .requestSchema(Schema.schema("UpdateCodeConventionRequest"))
                                        .pathParameters(parameterDescriptors)
                                        .requestFields(requestFieldDescription)
                                        .build())));
    }
}
