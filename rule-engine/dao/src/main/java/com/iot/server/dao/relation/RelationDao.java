package com.iot.server.dao.relation;

import com.iot.server.common.dao.Dao;
import com.iot.server.dao.entity.relation.RelationCompositeKey;
import com.iot.server.dao.entity.relation.RelationEntity;

import java.util.List;
import java.util.UUID;

public interface RelationDao extends Dao<RelationEntity, RelationCompositeKey> {
    List<RelationEntity> findAllByFromIds(List<UUID> fromIds);
}
