package com.iot.server.common.dto;


import com.iot.server.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseDto<ID extends Serializable> {
    private ID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createUid;
    private UUID updateUid;
    private boolean deleted;

    protected BaseDto(BaseEntity<ID> baseEntity) {
        if (baseEntity.getId() != null) {
            this.id = baseEntity.getId();
        }

        if (baseEntity.getCreateUid() != null) {
            this.createUid = baseEntity.getCreateUid();
        }

        if (baseEntity.getUpdateUid() != null) {
            this.updateUid = baseEntity.getUpdateUid();
        }

        this.createdAt = baseEntity.getCreatedAt();
        this.updatedAt = baseEntity.getUpdatedAt();
        this.deleted = baseEntity.isDeleted();
    }
}

