package com.iot.server.dao.entity;

import com.iot.server.common.entity.BaseEntity;
import com.iot.server.dao.dto.UserDto;
import com.iot.server.common.entity.EntityConstants;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = EntityConstants.USER_TABLE_NAME)
public class UserEntity extends BaseEntity<UUID> {
    @Column(name = EntityConstants.USER_EMAIL_PROPERTY, unique = true)
    private String email;

    @Column(name = EntityConstants.USER_FIRST_NAME_PROPERTY)
    private String firstName;

    @Column(name = EntityConstants.USER_LAST_NAME_PROPERTY)
    private String lastName;

    @Column(name = EntityConstants.USER_TENANT_ID_PROPERTY)
    private UUID tenantId;

    @Column(name = EntityConstants.USER_CUSTOMER_ID_PROPERTY)
    private UUID customerId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = EntityConstants.USER_ROLE_TABLE_NAME,
            joinColumns = @JoinColumn(name = EntityConstants.USER_ROLE_USER_ID_PROPERTY),
            inverseJoinColumns = @JoinColumn(name = EntityConstants.USER_ROLE_ROLE_ID_PROPERTY),
            foreignKey = @ForeignKey(name = "none"),
            inverseForeignKey = @ForeignKey(name = "none")
    )
    @ToString.Exclude
    private Set<RoleEntity> roles;

    public UserEntity(UserDto userDto) {
        super(userDto);
        this.email = userDto.getEmail();
        this.firstName = userDto.getFirstName();
        this.lastName = userDto.getLastName();
        this.tenantId = userDto.getTenantId();
        this.customerId = userDto.getCustomerId();
    }
}