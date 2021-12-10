package com.iot.server.common.entity;

import com.iot.server.common.dto.UserDto;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = EntityConstants.USER_TABLE_NAME)
public class UserEntity extends BaseEntity<UUID> {
    @Column(name = EntityConstants.USER_EMAIL_PROPERTY)
    private String email;

    @Column(name = EntityConstants.USER_FIRST_NAME_PROPERTY)
    private String firstName;

    @Column(name = EntityConstants.USER_LAST_NAME_PROPERTY)
    private String lastName;

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
        this.roles = userDto.getRoles()
                .stream()
                .map(RoleEntity::new)
                .collect(Collectors.toSet());
    }
}