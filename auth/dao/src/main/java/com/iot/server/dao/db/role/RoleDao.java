package com.iot.server.dao.db.role;

import com.iot.server.common.dao.Dao;

import com.iot.server.dao.entity.RoleEntity;
import java.util.UUID;

public interface RoleDao extends Dao<RoleEntity, UUID> {
    RoleEntity findByName(String name);
}
