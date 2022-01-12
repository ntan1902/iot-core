package com.iot.server.dao.relation;

import com.iot.server.common.dao.JpaAbstractDao;
import com.iot.server.dao.entity.relation.RelationCompositeKey;
import com.iot.server.dao.entity.relation.RelationEntity;
import com.iot.server.dao.repository.RelationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RelationDaoImpl extends JpaAbstractDao<RelationEntity, RelationCompositeKey> implements RelationDao {

    private final RelationRepository relationRepository;

    @Override
    protected JpaRepository<RelationEntity, RelationCompositeKey> getJpaRepository() {
        return relationRepository;
    }
}
