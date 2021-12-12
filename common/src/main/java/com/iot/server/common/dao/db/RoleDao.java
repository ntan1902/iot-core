package com.iot.server.common.dao.db;

import com.iot.server.common.entity.RoleEntity;

import java.util.UUID;

public interface RoleDao extends Dao<RoleEntity, UUID> {
    RoleEntity findByName(String name);
}
