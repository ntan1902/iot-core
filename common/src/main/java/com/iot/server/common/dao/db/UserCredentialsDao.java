package com.iot.server.common.dao.db;

import com.iot.server.common.entity.UserCredentialsEntity;

import java.util.UUID;

public interface UserCredentialsDao extends Dao<UserCredentialsEntity, UUID> {
    UserCredentialsEntity findByUserId(UUID userId);
}
