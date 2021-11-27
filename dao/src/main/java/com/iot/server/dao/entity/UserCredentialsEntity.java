package com.iot.server.dao.entity;

import com.iot.server.common.dto.UserCredentialsDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = EntityConstants.USER_CREDENTIALS_TABLE_NAME)
public class UserCredentialsEntity extends BaseEntity<UserCredentialsDto> {

    @Column(name = EntityConstants.USER_CREDENTIALS_USER_ID_PROPERTY, unique = true)
    private UUID userId;

    @Column(name = EntityConstants.USER_CREDENTIALS_PASSWORD_PROPERTY)
    private String password;

    @Column(name = EntityConstants.USER_CREDENTIALS_ENABLED_PROPERTY)
    private boolean enabled;

    @Column(name = EntityConstants.USER_CREDENTIALS_ACTIVATE_TOKEN_PROPERTY, unique = true)
    private String activateToken;

    @Column(name = EntityConstants.USER_CREDENTIALS_RESET_TOKEN_PROPERTY, unique = true)
    private String resetToken;

    @Override
    public UserCredentialsDto toDto() {
        final UserCredentialsDto dto = UserCredentialsDto
                .builder()
                .userId(userId)
                .enabled(enabled)
                .activateToken(activateToken)
                .password(password)
                .resetToken(resetToken)
                .build();

        super.toDto(dto);
        return dto;
    }
}
