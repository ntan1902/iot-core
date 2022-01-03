package com.iot.server.dao.repository;

import com.iot.server.dao.entity.TsKvCompositeKey;
import com.iot.server.dao.entity.TsKvEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TsKvRepository extends JpaRepository<TsKvEntity, TsKvCompositeKey> {
    List<TsKvEntity> findAllByEntityIdOrderByTsDesc(UUID entityId);
}
