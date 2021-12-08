package com.iot.server.common.service;

import com.iot.server.common.dto.UserCredentialsDto;
import com.iot.server.common.dto.UserDto;
import com.iot.server.common.request.RegisterRequest;

import java.util.UUID;

public interface UserService {
    UserDto registerUser(RegisterRequest registerRequest);

    UserDto findUserByEmail(String email);

    UserCredentialsDto findUserCredentialsByUserId(UUID userId);
}
