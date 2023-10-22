package com.pcmk.service.project;

import com.pcmk.domain.project.ProjectEntity;
import com.pcmk.dto.project.groundrule.GroundRuleElementDTO;
import com.pcmk.dto.project.groundrule.GroundRuleUpdateRequestDTO;
import com.pcmk.infra.fixture.ProjectEntityFixture;
import com.pcmk.infra.test.AbstractIntegrationTest;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@DisplayName("프로젝트 서비스 통합 테스트")
class ProjectServiceTest extends AbstractIntegrationTest {

    @Test
    @DisplayName("그라운드 룰을 수정한다")
    void updateGroundRule_pass() throws Exception {
        //given
        ProjectEntity project = projectRepository.save(ProjectEntityFixture.of());

        List<GroundRuleElementDTO> elements = new ArrayList<>();
        elements.add(GroundRuleElementDTO.of("name1", true));
        elements.add(GroundRuleElementDTO.of("name2", true));
        elements.add(GroundRuleElementDTO.of("name3", true));
        elements.add(GroundRuleElementDTO.of("name4", true));

        GroundRuleUpdateRequestDTO request = GroundRuleUpdateRequestDTO.builder()
                .elementDTOs(elements)
                .build();

        //when
        projectService.updateGroundRule(project.getProjectUUID(), request);

        //then
        ProjectEntity find = projectRepository.findByProjectUUID(project.getProjectUUID()).get();
        Assertions.assertThat(find.getGroundRule()).isNotNull();
        Assertions.assertThat(find.getGroundRule().getElements()).hasSize(4);
    }

}