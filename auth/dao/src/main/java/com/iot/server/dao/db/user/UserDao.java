package com.iot.server.dao.db.user;

import com.iot.server.common.dao.Dao;
import com.iot.server.dao.entity.UserEntity;
import java.util.UUID;

public interface UserDao extends Dao<UserEntity, UUID> {

   UserEntity findByEmail(String email);

   boolean existsByEmail(String email);
}
