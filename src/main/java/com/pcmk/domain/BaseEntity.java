package com.pcmk.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.sql.Timestamp;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false, columnDefinition = "datetime default CURRENT_TIMESTAMP NOT NULL COMMENT '생성 시간'")
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "modified_at", columnDefinition = "datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '수정 시간'")
    private Timestamp modifiedAt;

    @Column(name = "deleted_at", columnDefinition = "datetime COMMENT '삭제 시간'")
    private Timestamp deletedAt;
}
