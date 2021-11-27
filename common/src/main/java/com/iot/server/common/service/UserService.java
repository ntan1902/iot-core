package com.iot.server.common.service;

import com.iot.server.common.dto.UserDto;

public interface UserService {
    UserDto save(UserDto userDto);
}
