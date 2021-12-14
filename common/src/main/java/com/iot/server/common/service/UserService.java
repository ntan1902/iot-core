package com.iot.server.common.service;

import com.iot.server.common.dto.UserCredentialsDto;
import com.iot.server.common.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDto registerUser(UserDto userDto, String password);

    UserDto findUserWithRolesByEmail(String email);

    UserCredentialsDto findUserCredentialsByUserId(UUID userId);

    UserDto findUserWithExtraInfoById(UUID userId);

    UserDto saveUserWithAuthorities(UserDto userDto, List<String> authorities);
}
