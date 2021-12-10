package com.iot.server.common.dto;


import com.iot.server.common.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserDto extends BaseDto<UUID> {
    private String email;
    private String firstName;
    private String lastName;
    private Set<RoleDto> roles;

    public UserDto(UserEntity userEntity) {
        super(userEntity);
        this.email = userEntity.getEmail();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.roles = userEntity.getRoles()
                .stream()
                .map(RoleDto::new)
                .collect(Collectors.toSet());
    }
}

