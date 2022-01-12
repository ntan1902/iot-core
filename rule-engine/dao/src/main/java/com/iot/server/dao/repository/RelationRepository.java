package com.iot.server.dao.repository;

import com.iot.server.dao.entity.relation.RelationCompositeKey;
import com.iot.server.dao.entity.relation.RelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RelationRepository extends JpaRepository<RelationEntity, RelationCompositeKey> {
    List<RelationEntity> findAllByFromIdIn(List<UUID> fromIds);
}
