package com.iot.server.dao.entity;

import com.iot.server.common.dto.UserDto;
import com.iot.server.common.enums.AuthorityEnum;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = EntityConstants.USER_TABLE_NAME)
public class UserEntity extends BaseEntity<UserDto> {
    @Column(name = EntityConstants.USER_EMAIL_PROPERTY)
    private String email;

    @Column(name = EntityConstants.USER_FIRST_NAME_PROPERTY)
    private String firstName;

    @Column(name = EntityConstants.USER_LAST_NAME_PROPERTY)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = EntityConstants.USER_AUTHORITY_PROPERTY)
    private AuthorityEnum authority;

    public UserEntity(UserDto userDto) {
        super(userDto);

        this.firstName = userDto.getFirstName();
        this.lastName = userDto.getLastName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        UserEntity that = (UserEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public UserDto toDto() {
        final UserDto userDto = UserDto.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .authority(authority)
                .build();

        super.toDto(userDto);
        return userDto;
    }
}