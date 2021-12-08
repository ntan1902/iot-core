package com.iot.server.common.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto extends BaseDto {
    private String name;
}
