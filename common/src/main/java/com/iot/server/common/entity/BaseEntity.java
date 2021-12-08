package com.iot.server.common.entity;

import com.iot.server.common.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity<D extends BaseDto> implements ToDto<D> {
    @Id
    @Column(name = EntityConstants.ID_PROPERTY, columnDefinition = "uuid")
    protected UUID id;

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

    protected BaseEntity(BaseDto baseDto) {
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

    public final void toDto(final D dto) {
        if (id != null) {
            dto.setId(id);
        }

        if (createUid != null) {
            dto.setCreateUid(createUid);
        }

        if (updateUid != null) {
            dto.setUpdateUid(updateUid);
        }

        dto.setCreatedAt(createdAt);
        dto.setUpdatedAt(updatedAt);
        dto.setDeleted(deleted);
    }
}
