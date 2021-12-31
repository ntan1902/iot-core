package com.iot.server.auth.service;

import com.iot.server.dao.dto.UserCredentialsDto;

public interface SecurityService {

    void validateUserCredentials(UserCredentialsDto userCredentials,
                                 String email,
                                 String password);
}
