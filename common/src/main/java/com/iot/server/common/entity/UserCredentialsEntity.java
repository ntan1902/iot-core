package com.iot.server.common.entity;

import com.iot.server.common.dto.UserCredentialsDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = EntityConstants.USER_CREDENTIALS_TABLE_NAME)
public class UserCredentialsEntity extends BaseEntity<UUID> {
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = EntityConstants.USER_CREDENTIALS_USER_ID_PROPERTY,
            referencedColumnName = EntityConstants.ID_PROPERTY,
            foreignKey = @ForeignKey(name = "none"))
    private UserEntity user;

    @Column(name = EntityConstants.USER_CREDENTIALS_PASSWORD_PROPERTY)
    private String password;

    @Column(name = EntityConstants.USER_CREDENTIALS_ENABLED_PROPERTY)
    private boolean enabled;

    @Column(name = EntityConstants.USER_CREDENTIALS_ACTIVATE_TOKEN_PROPERTY, unique = true)
    private String activateToken;

    @Column(name = EntityConstants.USER_CREDENTIALS_RESET_TOKEN_PROPERTY, unique = true)
    private String resetToken;

    public UserCredentialsEntity(UserCredentialsDto userCredentialsDto) {
        super(userCredentialsDto);
        this.user.setId(userCredentialsDto.getUserId());
        this.password = userCredentialsDto.getPassword();
        this.enabled = userCredentialsDto.isEnabled();
        this.activateToken = userCredentialsDto.getActivateToken();
        this.resetToken = userCredentialsDto.getResetToken();
    }
}
