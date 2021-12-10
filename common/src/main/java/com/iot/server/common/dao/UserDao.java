package com.iot.server.common.dao;

import com.iot.server.common.entity.UserEntity;

import java.util.UUID;

public interface UserDao extends Dao<UserEntity, UUID> {

    UserEntity findByEmail(String email);

}
