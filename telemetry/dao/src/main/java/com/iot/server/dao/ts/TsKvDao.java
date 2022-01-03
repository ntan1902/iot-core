package com.iot.server.dao.ts;

import com.iot.server.common.dao.Dao;
import com.iot.server.dao.entity.TsKvCompositeKey;
import com.iot.server.dao.entity.TsKvEntity;

import java.util.List;
import java.util.UUID;

public interface TsKvDao extends Dao<TsKvEntity, TsKvCompositeKey> {
    void save(List<TsKvEntity> tsKvEntities);

    List<TsKvEntity> findTsKvByEntityId(UUID entityId);
}
