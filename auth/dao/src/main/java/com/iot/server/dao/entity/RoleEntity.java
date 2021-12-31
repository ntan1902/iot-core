package com.iot.server.dao.entity;

import com.iot.server.common.entity.BaseEntity;
import com.iot.server.dao.dto.RoleDto;
import com.iot.server.common.entity.EntityConstants;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = EntityConstants.ROLE_TABLE_NAME)
public class RoleEntity extends BaseEntity<UUID> {

    @Column(name = EntityConstants.ROLE_NAME_PROPERTY, unique = true)
    private String name;

    public RoleEntity(RoleDto roleDto) {
        super(roleDto);
        this.name = roleDto.getName();
    }
}
