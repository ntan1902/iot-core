package com.iot.server.dao.repository;

import com.iot.server.dao.entity.latest.TsKvLatestCompositeKey;
import com.iot.server.dao.entity.latest.TsKvLatestEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TsKvLatestRepository extends JpaRepository<TsKvLatestEntity, TsKvLatestCompositeKey> {

    List<TsKvLatestEntity> findAllByEntityId(UUID entityId, Sort sort);
}
