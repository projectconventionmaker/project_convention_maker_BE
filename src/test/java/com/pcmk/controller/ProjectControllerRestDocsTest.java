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
import com.pcmk.domain.project.codeconvention.CodeConvention;
import com.pcmk.domain.project.codeconvention.CodeConventionItem;
import com.pcmk.domain.project.commitconvention.CommitConvention;
import com.pcmk.domain.project.commitconvention.CommitConventionItem;
import com.pcmk.domain.project.groundrule.GroundRule;
import com.pcmk.domain.project.groundrule.GroundRuleItem;
import com.pcmk.domain.project.techstack.TechStack;
import com.pcmk.domain.project.techstack.TechStackElement;
import com.pcmk.dto.project.ProjectDTO;
import com.pcmk.dto.project.ProjectDTO.ProjectDetail;
import com.pcmk.dto.project.request.CodeConventionUpdateRequestDTO;
import com.pcmk.dto.project.request.CommitConventionUpdateRequestDTO;
import com.pcmk.dto.project.request.GroundRuleUpdateRequestDTO;
import com.pcmk.dto.project.request.ProjectCreateRequestDTO;
import com.pcmk.dto.project.request.ProjectUpdateRequestDTO;
import com.pcmk.dto.project.request.techstack.TechStackUpdateRequestDTO;
import com.pcmk.dto.project.response.TechStackUpdateResponseDTO;
import com.pcmk.infra.test.AbstractRestDocsTest;
import com.pcmk.service.project.ProjectService;
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
    public static final String PROJECT_INTRODUCTION = "보일러 플레이트 프로젝트";
    public static final String PROJECT_DETAIL = "프로젝트 상세 설명";
    public static final String TEAM_NAME = "Team name";
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

        ProjectEntity projectEntity = ProjectEntity.builder()
                .projectName(PROJECT_NAME)
                .projectUUID(PROJECT_UUID)
                .build();

        ProjectDTO response = ProjectDTO.fromEntity(projectEntity);

        BDDMockito.given(projectService.createProject(request.getProjectName()))
                .willReturn(response);

        var requestFieldDescription = new FieldDescriptor[]{
                fieldWithPath("project_name").type(STRING).description("(Required) 프로젝트 이름")
        };

        var responseFieldDescription = new FieldDescriptor[]{
                fieldWithPath("project_detail").type(OBJECT).description("이름"),
                fieldWithPath("project_detail.project_name").type(STRING).description("프로젝트명"),
                fieldWithPath("project_detail.project_uuid").type(STRING).description("UUID"),
                fieldWithPath("project_detail.team_name").type(NULL).description("(Nullable) 팀명"),
                fieldWithPath("project_detail.introduction").type(NULL).description("(Nullable) 프로젝트 한 줄 소개"),
                fieldWithPath("project_detail.detail").type(NULL).description("(Nullable)  프로젝트 상세")
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
        ProjectEntity projectEntity = ProjectEntity.builder()
                .projectName(PROJECT_NAME)
                .teamName(TEAM_NAME)
                .projectUUID(PROJECT_UUID)
                .detail(PROJECT_DETAIL)
                .introduction(PROJECT_INTRODUCTION)
                .build();

        List<CodeConventionItem> codeConventionItems = new ArrayList<>();
        codeConventionItems.add(new CodeConventionItem("클래스명은 ~로 한다"));
        codeConventionItems.add(new CodeConventionItem("테스트 메서드명은 ~로 한다"));
        codeConventionItems.add(new CodeConventionItem("어노테이션명은 ~로 한다"));

        List<CommitConventionItem> commitConventionItems = new ArrayList<>();
        commitConventionItems.add(new CommitConventionItem("[feat]: 기능 추가,삭제, 변경", true));
        commitConventionItems.add(new CommitConventionItem("[fix]: 버그,오류 수정", true));
        commitConventionItems.add(new CommitConventionItem("[refactor]: 코드 리팩토링:", true));

        List<GroundRuleItem> items = new ArrayList<>();
        items.add(new GroundRuleItem("님 금지! 극 존대 금지!", true));
        items.add(new GroundRuleItem("ZEP에서 캠은 켜지 않을 거에요", false));
        items.add(new GroundRuleItem("스프린트는 협업과 프로세스를 배우는 시간이지 포트폴리오나 사이드 프로젝트를 하는 시간이 아닙니다", true));

        ProjectDTO response = ProjectDTO.builder()
                .projectDetail(ProjectDetail.of(projectEntity))
                .groundRule(GroundRule.of(items))
                .codeConvention(CodeConvention.of(codeConventionItems))
                .commitConvention(CommitConvention.of(commitConventionItems))
                .build();

        BDDMockito.given(projectService.getProject(PROJECT_UUID))
                .willReturn(response);

        var parameterDescriptors = new ParameterDescriptor[]{
                parameterWithName("projectUUID").description("(Required) UUID")
        };

        String identifier = "get-v1-get-project";
        String summary = "프로젝트 조회 API";
        String description = """
                - 존재하지 않는 프로젝트 UUID로 요청시 1000 에러 코드
                """;

        var responseFieldDescription = new FieldDescriptor[]{
                fieldWithPath("project_detail").type(OBJECT).description("이름"),
                fieldWithPath("project_detail.project_name").type(STRING).description("프로젝트명"),
                fieldWithPath("project_detail.project_uuid").type(STRING).description("UUID"),
                fieldWithPath("project_detail.team_name").type(STRING).description("팀명"),
                fieldWithPath("project_detail.introduction").type(STRING).description("프로젝트 한 줄 소개"),
                fieldWithPath("project_detail.detail").type(STRING).description("프로젝트 상세"),
                fieldWithPath("tech_stack").type(OBJECT).description("기술 스택"),
                fieldWithPath("tech_stack.languages").type(ARRAY).description("언어"),
                fieldWithPath("tech_stack.languages[].name").type(STRING).description("언어 이름"),
                fieldWithPath("tech_stack.frameworks").type(ARRAY).description("프레임워크"),
                fieldWithPath("tech_stack.frameworks[].name").type(STRING).description("프레임워크 이름"),
                fieldWithPath("tech_stack.styles").type(ARRAY).description("스타일"),
                fieldWithPath("tech_stack.styles[].name").type(STRING).description("스타일 이름"),
                fieldWithPath("ground_rule").type(OBJECT).description("그라운드 룰"),
                fieldWithPath("ground_rule.items").type(ARRAY).description("그라운드 룰 항목 리스트"),
                fieldWithPath("ground_rule.items[].name").type(STRING).description("항목 이름"),
                fieldWithPath("ground_rule.items[].checked").type(BOOLEAN).description("항목이 채택되었는지"),
                fieldWithPath("commit_convention").type(OBJECT).description("커밋 컨벤션"),
                fieldWithPath("commit_convention.items").type(ARRAY).description("커밋 컨벤션 항목 리스트"),
                fieldWithPath("commit_convention.items[].name").type(STRING).description("항목 이름"),
                fieldWithPath("commit_convention.items[].checked").type(BOOLEAN).description("항목이 채택되었는지"),
                fieldWithPath("code_convention").type(OBJECT).description("코드 컨벤션"),
                fieldWithPath("code_convention.items").type(ARRAY).description("코드 컨벤션 항목 리스트"),
                fieldWithPath("code_convention.items[].name").type(STRING).description("항목 이름")
        };

        //when //then
        mockMvc.perform(
                        get("/api/v1/projects/{projectUUID}", PROJECT_UUID))
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
        ProjectUpdateRequestDTO request = ProjectUpdateRequestDTO.builder()
                .projectName("Changed project name")
                .teamName("Changed team name")
                .introduction("Changed project introduction")
                .detail("Change project detail")
                .build();

        ProjectEntity projectEntity = ProjectEntity.builder()
                .projectName(PROJECT_NAME)
                .teamName(TEAM_NAME)
                .projectUUID(PROJECT_UUID)
                .detail(PROJECT_DETAIL)
                .introduction(PROJECT_INTRODUCTION)
                .build();

        ProjectDTO response = ProjectDTO.builder()
                .projectDetail(ProjectDetail.of(projectEntity))
                .build();

        BDDMockito.given(projectService.updateProject(PROJECT_UUID, request))
                .willReturn(response);

        var parameterDescriptors = new ParameterDescriptor[]{
                parameterWithName("projectUUID").description("(Required) UUID")
        };

        var requestFieldDescription = new FieldDescriptor[]{
                fieldWithPath("project_name").type(STRING).description("(Required) 프로젝트명"),
                fieldWithPath("team_name").type(STRING).description("(Required) 팀명"),
                fieldWithPath("introduction").type(STRING).description("(Required) 프로젝트 한 줄 소개"),
                fieldWithPath("detail").type(STRING).description("(Required) 프로젝트 상세"),
        };

        var responseFieldDescription = new FieldDescriptor[]{
                fieldWithPath("project_detail").type(OBJECT).description("이름"),
                fieldWithPath("project_detail.project_name").type(STRING).description("프로젝트명"),
                fieldWithPath("project_detail.project_uuid").type(STRING).description("UUID"),
                fieldWithPath("project_detail.team_name").type(STRING).description("팀명"),
                fieldWithPath("project_detail.introduction").type(STRING).description("프로젝트 한 줄 소개"),
                fieldWithPath("project_detail.detail").type(STRING).description("프로젝트 상세")
        };

        String identifier = "put-v1-update-project";
        String summary = "프로젝트 개요 수정 API";
        String description = """
                - 필수값 비어있는 경우 400 에러 코드
                - 존재하지 않는 프로젝트 UUID로 요청시 1000 에러 코드
                - 변경된 프로젝트명이 사용중인 경우 1001 에러 코드
                """;

        //when //then
        mockMvc.perform(
                        put("/api/v1/projects/{projectUUID}", PROJECT_UUID)
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
                                        .responseSchema(Schema.schema("UpdateProjectResponse"))
                                        .pathParameters(parameterDescriptors)
                                        .requestFields(requestFieldDescription)
                                        .responseFields(responseFieldDescription)
                                        .build())));
    }

    @Test
    @DisplayName("수정: 프로젝트 기술스택")
    void updateTechStack_restDocs() throws Exception {
        //given

        //given data
        List<TechStackElement> techStackElementList = new ArrayList<>();
        techStackElementList.add(TechStackElement.of("Language", List.of("Java", "Kotlin", "Golang")));
        techStackElementList.add(
                TechStackElement.of("DB", List.of("MySQL", "MariaDB", "DynamoDB", "MongoDB", "Redis")));
        techStackElementList.add(TechStackElement.of("Framework", List.of("Spring", "FastAPI", "Django")));

        TechStack techStack = TechStack.of(techStackElementList);

        TechStackUpdateRequestDTO request = TechStackUpdateRequestDTO.of(techStack);

        TechStackUpdateResponseDTO response = TechStackUpdateResponseDTO.of(techStack);

        BDDMockito.given(projectService.updateTechStack(PROJECT_UUID, request))
                .willReturn(response);

        //given restdocs
        var parameterDescriptors = new ParameterDescriptor[]{
                parameterWithName("projectUUID").description("(Required) UUID")
        };

        var requestFieldDescription = new FieldDescriptor[]{
                fieldWithPath("tech_stack").type(ARRAY).description("(Required) 테크 스택 리스트"),
                fieldWithPath("tech_stack[].category").type(STRING).description("(Required) 스택 카테고리"),
                fieldWithPath("tech_stack[].names").type(ARRAY).description("(Required) 기술 이름")
        };

        var responseFieldDescription = new FieldDescriptor[]{
                fieldWithPath("tech_stack").type(ARRAY).description("테크 스택 리스트"),
                fieldWithPath("tech_stack[].category").type(STRING).description("스택 카테고리"),
                fieldWithPath("tech_stack[].names").type(ARRAY).description("기술 이름")
        };

        String identifier = "put-v1-update-project-tech-stack";
        String summary = "프로젝트 기술 스택 수정 API";
        String description = """
                - 필수값 비어있는 경우 400 에러 코드
                - 존재하지 않는 프로젝트 UUID로 요청시 1000 에러 코드
                """;

        //when //then
        mockMvc.perform(
                        put("/api/v1/projects/{projectUUID}/tech-stack", PROJECT_UUID)
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
                                        .responseSchema(Schema.schema("UpdateTechStackResponse"))
                                        .pathParameters(parameterDescriptors)
                                        .requestFields(requestFieldDescription)
                                        .responseFields(responseFieldDescription)
                                        .build())));
    }

    @Test
    @DisplayName("수정: 프로젝트 그라운드 룰")
    void updateGroundRules_restDocs() throws Exception {
        //given
        List<GroundRuleItem> items = new ArrayList<>();
        items.add(new GroundRuleItem("님 금지! 극 존대 금지!", true));
        items.add(new GroundRuleItem("ZEP에서 캠은 켜지 않을 거에요", false));
        items.add(new GroundRuleItem("스프린트는 협업과 프로세스를 배우는 시간이지 포트폴리오나 사이드 프로젝트를 하는 시간이 아닙니다", true));

        GroundRuleUpdateRequestDTO request = GroundRuleUpdateRequestDTO.builder()
                .items(items)
                .build();

        GroundRule groundRule = GroundRule.of(items);

        ProjectEntity projectEntity = ProjectEntity.builder()
                .projectName(PROJECT_NAME)
                .projectUUID(PROJECT_UUID)
                .teamName(TEAM_NAME)
                .detail(PROJECT_DETAIL)
                .introduction(PROJECT_INTRODUCTION)
                .build();

        ProjectDTO response = ProjectDTO.builder()
                .projectDetail(ProjectDetail.of(projectEntity))
                .groundRule(groundRule)
                .build();

        BDDMockito.given(projectService.updateGroundRule(PROJECT_UUID, request))
                .willReturn(response);

        var parameterDescriptors = new ParameterDescriptor[]{
                parameterWithName("projectUUID").description("(Required) UUID")
        };

        var requestFieldDescription = new FieldDescriptor[]{
                fieldWithPath("items").type(ARRAY).description("(Required) 그라운드 룰 항목"),
                fieldWithPath("items[].name").type(STRING).description("(Required) 항목 이름"),
                fieldWithPath("items[].checked").type(BOOLEAN).description("(Required) 항목이 채택되었는지")
        };

        var responseFieldDescription = new FieldDescriptor[]{
                fieldWithPath("project_detail").type(OBJECT).description("이름"),
                fieldWithPath("project_detail.project_name").type(STRING).description("프로젝트명"),
                fieldWithPath("project_detail.project_uuid").type(STRING).description("UUID"),
                fieldWithPath("project_detail.team_name").type(STRING).description("(Nullable) 팀명"),
                fieldWithPath("project_detail.introduction").type(STRING).description("(Nullable) 프로젝트 한 줄 소개"),
                fieldWithPath("project_detail.detail").type(STRING).description("(Nullable)  프로젝트 상세"),
                fieldWithPath("ground_rule").type(OBJECT).description("그라운드 룰"),
                fieldWithPath("ground_rule.items").type(ARRAY).description("그라운드 룰 항목 리스트"),
                fieldWithPath("ground_rule.items[].name").type(STRING).description("항목 이름"),
                fieldWithPath("ground_rule.items[].checked").type(BOOLEAN).description("항목이 채택되었는지")
        };

        String identifier = "put-v1-update-project-ground-rule";
        String summary = "프로젝트 그라운드 룰 수정 API";
        String description = """
                - 필수값 비어있는 경우 400 에러 코드
                - 존재하지 않는 프로젝트 UUID로 요청시 1000 에러 코드
                """;

        //when //then
        mockMvc.perform(
                        put("/api/v1/projects/{projectUUID}/ground-rules", PROJECT_UUID)
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
                                        .responseSchema(Schema.schema("UpdateGroundRuleResponse"))
                                        .pathParameters(parameterDescriptors)
                                        .requestFields(requestFieldDescription)
                                        .responseFields(responseFieldDescription)
                                        .build())));
    }

    @Test
    @DisplayName("수정: 프로젝트 커밋 컨벤션")
    void updateCommitConventions_restDocs() throws Exception {
        //given
        List<CommitConventionItem> items = new ArrayList<>();
        items.add(new CommitConventionItem("[feat]: 기능 추가,삭제, 변경", true));
        items.add(new CommitConventionItem("[fix]: 버그,오류 수정", true));
        items.add(new CommitConventionItem("[refactor]: 코드 리팩토링:", true));

        CommitConventionUpdateRequestDTO request = CommitConventionUpdateRequestDTO.builder()
                .items(items)
                .build();

        CommitConvention commitConvention = CommitConvention.of(items);

        ProjectEntity projectEntity = ProjectEntity.builder()
                .projectName(PROJECT_NAME)
                .projectUUID(PROJECT_UUID)
                .teamName(TEAM_NAME)
                .detail(PROJECT_DETAIL)
                .introduction(PROJECT_INTRODUCTION)
                .build();

        ProjectDTO response = ProjectDTO.builder()
                .projectDetail(ProjectDetail.of(projectEntity))
                .commitConvention(commitConvention)
                .build();

        BDDMockito.given(projectService.updateCommitConvention(PROJECT_UUID, request))
                .willReturn(response);

        var parameterDescriptors = new ParameterDescriptor[]{
                parameterWithName("projectUUID").description("(Required) UUID")
        };

        var requestFieldDescription = new FieldDescriptor[]{
                fieldWithPath("items").type(ARRAY).description("(Required) 커밋 컨벤션 항목"),
                fieldWithPath("items[].name").type(STRING).description("(Required) 항목 이름"),
                fieldWithPath("items[].checked").type(BOOLEAN).description("(Required) 항목이 채택되었는지")
        };

        var responseFieldDescription = new FieldDescriptor[]{
                fieldWithPath("project_detail").type(OBJECT).description("이름"),
                fieldWithPath("project_detail.project_name").type(STRING).description("프로젝트명"),
                fieldWithPath("project_detail.project_uuid").type(STRING).description("UUID"),
                fieldWithPath("project_detail.team_name").type(STRING).description("(Nullable) 팀명"),
                fieldWithPath("project_detail.introduction").type(STRING).description("(Nullable) 프로젝트 한 줄 소개"),
                fieldWithPath("project_detail.detail").type(STRING).description("(Nullable)  프로젝트 상세"),
                fieldWithPath("commit_convention").type(OBJECT).description("커밋 컨벤션"),
                fieldWithPath("commit_convention.items").type(ARRAY).description("커밋 컨벤션 항목 리스트"),
                fieldWithPath("commit_convention.items[].name").type(STRING).description("항목 이름"),
                fieldWithPath("commit_convention.items[].checked").type(BOOLEAN).description("항목이 채택되었는지")
        };

        String identifier = "put-v1-update-project-commit-conventions";
        String summary = "프로젝트 커밋 컨벤션 수정 API";
        String description = """
                - 필수값 비어있는 경우 400 에러 코드
                - 존재하지 않는 프로젝트 UUID로 요청시 1000 에러 코드
                """;

        //when //then
        mockMvc.perform(
                        put("/api/v1/projects/{projectUUID}/commit-conventions", PROJECT_UUID)
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
                                        .responseSchema(Schema.schema("UpdateCommitConventionResponse"))
                                        .pathParameters(parameterDescriptors)
                                        .requestFields(requestFieldDescription)
                                        .responseFields(responseFieldDescription)
                                        .build())));
    }

    @Test
    @DisplayName("수정: 프로젝트 코드 컨벤션")
    void updateCodeConventions_restDocs() throws Exception {
        //given
        List<CodeConventionItem> items = new ArrayList<>();
        items.add(new CodeConventionItem("클래스명은 ~로 한다"));
        items.add(new CodeConventionItem("테스트 메서드명은 ~로 한다"));
        items.add(new CodeConventionItem("어노테이션명은 ~로 한다"));

        CodeConventionUpdateRequestDTO request = CodeConventionUpdateRequestDTO.of(items);

        CodeConvention codeConvention = CodeConvention.of(items);

        ProjectEntity projectEntity = ProjectEntity.builder()
                .projectName(PROJECT_NAME)
                .projectUUID(PROJECT_UUID)
                .teamName(TEAM_NAME)
                .detail(PROJECT_DETAIL)
                .introduction(PROJECT_INTRODUCTION)
                .build();

        ProjectDTO response = ProjectDTO.builder()
                .projectDetail(ProjectDetail.of(projectEntity))
                .codeConvention(codeConvention)
                .build();

        BDDMockito.given(projectService.updateCodeConvention(PROJECT_UUID, request))
                .willReturn(response);

        var parameterDescriptors = new ParameterDescriptor[]{
                parameterWithName("projectUUID").description("(Required) UUID")
        };

        var requestFieldDescription = new FieldDescriptor[]{
                fieldWithPath("items").type(ARRAY).description("(Required) 코드 컨벤션 항목"),
                fieldWithPath("items[].name").type(STRING).description("(Required) 항목 이름")
        };

        var responseFieldDescription = new FieldDescriptor[]{
                fieldWithPath("project_detail").type(OBJECT).description("프로젝트 정보"),
                fieldWithPath("project_detail.project_name").type(STRING).description("프로젝트명"),
                fieldWithPath("project_detail.project_uuid").type(STRING).description("UUID"),
                fieldWithPath("project_detail.team_name").type(STRING).description("(Nullable) 팀명"),
                fieldWithPath("project_detail.introduction").type(STRING).description("(Nullable) 프로젝트 한 줄 소개"),
                fieldWithPath("project_detail.detail").type(STRING).description("(Nullable) 프로젝트 상세"),
                fieldWithPath("code_convention").type(OBJECT).description("코드 컨벤션"),
                fieldWithPath("code_convention.items").type(ARRAY).description("코드 컨벤션 항목 리스트"),
                fieldWithPath("code_convention.items[].name").type(STRING).description("항목 이름")
        };

        String identifier = "put-v1-update-project-code-conventions";
        String summary = "프로젝트 코드 컨벤션 수정 API";
        String description = """
                - 필수값 비어있는 경우 400 에러 코드
                - 존재하지 않는 프로젝트 UUID로 요청시 1000 에러 코드
                """;

        //when //then
        mockMvc.perform(
                        put("/api/v1/projects/{projectUUID}/code-conventions", PROJECT_UUID)
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
                                        .responseSchema(Schema.schema("UpdateCodeConventionResponse"))
                                        .pathParameters(parameterDescriptors)
                                        .requestFields(requestFieldDescription)
                                        .responseFields(responseFieldDescription)
                                        .build())));
    }
}
