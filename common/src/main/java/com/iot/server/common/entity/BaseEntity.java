package com.iot.server.common.entity;

import com.iot.server.common.dto.BaseDto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> {
    @Id
    @Column(name = EntityConstants.ID_PROPERTY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected ID id;

    @Column(name = EntityConstants.CREATE_UID_PROPERTY, columnDefinition = "uuid")
    protected UUID createUid;

    @Column(name = EntityConstants.UPDATE_UID_PROPERTY, columnDefinition = "uuid")
    protected UUID updateUid;

    @Column(name = EntityConstants.DELETED_PROPERTY)
    protected boolean deleted;

    @Column(name = EntityConstants.CREATED_AT_PROPERTY, updatable = false)
    protected LocalDateTime createdAt;

    @Column(name = EntityConstants.UPDATED_AT_PROPERTY)
    protected LocalDateTime updatedAt;

    protected BaseEntity(BaseDto<ID> baseDto) {
        if (baseDto.getId() != null) {
            this.id = baseDto.getId();
        }

        if (baseDto.getCreateUid() != null) {
            this.createUid = baseDto.getCreateUid();
        }

        if (baseDto.getUpdateUid() != null) {
            this.updateUid = baseDto.getUpdateUid();
        }

        this.createdAt = baseDto.getCreatedAt();
        this.updatedAt = baseDto.getUpdatedAt();
        this.deleted = baseDto.isDeleted();
    }
}
