package com.iot.server.application.model;

import com.iot.server.common.dto.RoleDto;
import com.iot.server.common.dto.UserDto;
import com.iot.server.common.enums.AuthorityEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityUser extends UserDto {
    private boolean enabled;
    private Collection<GrantedAuthority> authorities;

    public SecurityUser(UserDto user, boolean enabled, Collection<RoleDto> roles) {
        this.setId(user.getId());
        this.setEmail(user.getEmail());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setCreatedAt(user.getCreatedAt());
        this.setUpdatedAt(user.getUpdatedAt());
        this.enabled = enabled;

        this.authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    public void setAuthorities(List<AuthorityEnum> authorities) {
        this.authorities = authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.name()))
                .collect(Collectors.toSet());
    }

//    public Collection<GrantedAuthority> getAuthorities() {
//        if (authorities == null || authorities.isEmpty()) {
//            authorities = Collections.singletonList(
//                    new SimpleGrantedAuthority(this.getAuthority().name())
//            );
//        }
//        return authorities;
//    }

}