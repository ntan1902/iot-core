package com.iot.server.common.dto;

import com.iot.server.common.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class RoleDto extends BaseDto<UUID> {
    private String name;

    public RoleDto(RoleEntity roleEntity) {
        super(roleEntity);
        this.name = roleEntity.getName();
    }

}
