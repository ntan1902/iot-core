package com.iot.server.common.entity;

import com.iot.server.common.dto.RoleDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = EntityConstants.ROLE_TABLE_NAME)
public class RoleEntity extends BaseEntity<RoleDto> {

    @Column(name = EntityConstants.ROLE_NAME_PROPERTY)
    private String name;

    public RoleEntity(RoleDto roleDto) {
        super(roleDto);
        this.name = roleDto.getName();
    }

    @Override
    public RoleDto toDto() {
        final RoleDto roleDto = RoleDto.builder()
                .name(name)
                .build();
        super.toDto(roleDto);
        return roleDto;
    }
}
