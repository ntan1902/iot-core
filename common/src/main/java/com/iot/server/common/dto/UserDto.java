package com.iot.server.common.dto;


import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends BaseDto {

    private String email;
    private String firstName;
    private String lastName;
    private Set<RoleDto> roles;
}

