package com.iot.server.application.service;

import com.iot.server.common.dto.UserCredentialsDto;

public interface SecurityService {

    void validateUserCredentials(UserCredentialsDto userCredentials,
                                 String email,
                                 String password);
}
