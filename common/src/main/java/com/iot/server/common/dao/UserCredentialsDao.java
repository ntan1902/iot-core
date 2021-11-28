package com.iot.server.common.dao;

import com.iot.server.common.dto.UserCredentialsDto;

import java.util.UUID;

public interface UserCredentialsDao extends Dao<UserCredentialsDto, UUID> {
    UserCredentialsDto findByUserId(UUID userId);
}
