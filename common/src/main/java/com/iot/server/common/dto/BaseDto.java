package com.iot.server.common.dto;


import com.iot.server.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseDto<ID extends Serializable> {
    protected ID id;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected UUID createUid;
    protected UUID updateUid;
    protected boolean deleted;

    protected Map<String, Object> extraInfo;

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

