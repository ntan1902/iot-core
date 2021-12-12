package com.iot.server.common.dto;

import com.iot.server.common.entity.UserCredentialsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserCredentialsDto extends BaseDto<UUID> {
    private UUID userId;
    private boolean enabled;
    private String password;
    private String activateToken;
    private String resetToken;

    public UserCredentialsDto(UserCredentialsEntity userCredentialsEntity) {
        super(userCredentialsEntity);
        this.password = userCredentialsEntity.getPassword();
        this.enabled = userCredentialsEntity.isEnabled();
        this.activateToken = userCredentialsEntity.getActivateToken();
        this.resetToken = userCredentialsEntity.getResetToken();
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
