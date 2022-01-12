package com.iot.server.dao.relation;

import com.iot.server.common.dao.JpaAbstractDao;
import com.iot.server.dao.entity.relation.RelationCompositeKey;
import com.iot.server.dao.entity.relation.RelationEntity;
import com.iot.server.dao.repository.RelationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class RelationDaoImpl implements RelationDao {

    private final RelationRepository relationRepository;

    @Override
    public List<RelationEntity> findAll() {
        return null;
    }

    @Override
    public RelationEntity findById(RelationCompositeKey relationCompositeKey) {
        return null;
    }

    @Override
    public RelationEntity save(RelationEntity entity) {
        return null;
    }

    @Override
    public boolean removeById(RelationCompositeKey relationCompositeKey) {
        return false;
    }
}
