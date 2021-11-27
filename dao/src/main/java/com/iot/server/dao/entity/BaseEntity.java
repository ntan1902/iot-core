package com.iot.server.dao.entity;

import com.iot.server.common.dto.BaseDto;
import com.iot.server.dao.ToDto;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
public abstract class BaseEntity<D extends BaseDto> implements ToDto<D> {
  @Id
  @Column(name = EntityConstants.ID_PROPERTY, columnDefinition = "uuid")
  protected UUID id;

  @Column(name = EntityConstants.CREATED_AT_PROPERTY, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = EntityConstants.UPDATED_AT_PROPERTY)
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  protected BaseEntity(BaseDto baseDto) {
    if (baseDto.getId() != null) {
      this.id = baseDto.getId();
    }

    this.createdAt = baseDto.getCreatedAt();
    this.updatedAt = baseDto.getUpdatedAt();
  }

  public final void toDto(final D dto) {
    dto.setId(id);
    dto.setCreatedAt(createdAt);
    dto.setUpdatedAt(updatedAt);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    BaseEntity<?> that = (BaseEntity<?>) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
