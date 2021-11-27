package com.iot.server.application.security.model;

import com.iot.server.common.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class SecurityUser extends UserDto {
    private Collection<GrantedAuthority> authorities;
    private boolean enabled;

    public SecurityUser(UserDto userDto, boolean enabled) {
        this.setId(userDto.getId());
        this.setEmail(userDto.getEmail());
        this.setFirstName(userDto.getFirstName());
        this.setLastName(userDto.getLastName());
        this.setCreatedAt(userDto.getCreatedAt());
        this.setUpdatedAt(userDto.getUpdatedAt());
        this.enabled = enabled;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        if (authorities == null || authorities.isEmpty()) {
            authorities = Collections.singletonList(
                    new SimpleGrantedAuthority(this.getAuthority().name())
            );
        }
        return authorities;
    }

}
