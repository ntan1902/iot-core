package com.iot.server.common.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class UserCredentialsDto extends BaseDto {
    private UUID userId;
    private boolean enabled;
    private String password;
    private String activateToken;
    private String resetToken;
}
