package com.iot.server.dao.repository;

import com.iot.server.dao.entity.TsKvCompositeKey;
import com.iot.server.dao.entity.TsKvEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TsKvRepository extends JpaRepository<TsKvEntity, TsKvCompositeKey> {
}
