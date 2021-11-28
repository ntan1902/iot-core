package com.iot.server.common.service;

import com.iot.server.common.dto.UserCredentialsDto;
import com.iot.server.common.dto.UserDto;

import java.util.UUID;

public interface UserService {
    UserDto saveUser(UserDto userDto, String password);

    UserDto findUserByEmail(String email);

    UserCredentialsDto findUserCredentialsByUserId(UUID userId);
}
