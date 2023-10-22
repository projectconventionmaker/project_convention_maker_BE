package com.pcmk.domain.project;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    boolean existsByProjectName(String projectName);

    Optional<ProjectEntity> findByProjectUUID(String uuid);

    Optional<ProjectEntity> findByProjectName(String projectName);
}
