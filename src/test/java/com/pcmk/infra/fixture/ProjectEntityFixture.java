package com.pcmk.infra.fixture;

import com.pcmk.domain.project.ProjectEntity;
import java.util.UUID;

public abstract class ProjectEntityFixture {

    public static ProjectEntity of() {
        return ProjectEntity.builder()
                .projectName(UUID.randomUUID().toString())
                .projectUUID(UUID.randomUUID().toString())
                .build();
    }
}
