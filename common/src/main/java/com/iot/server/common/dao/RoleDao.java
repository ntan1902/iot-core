package com.iot.server.common.dao;

import com.iot.server.common.dto.RoleDto;

import java.util.UUID;

public interface RoleDao extends Dao<RoleDto, UUID> {
    RoleDto findByName(String name);
}
