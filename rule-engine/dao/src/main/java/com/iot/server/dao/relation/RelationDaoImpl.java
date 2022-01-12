package com.iot.server.dao.relation;

import com.iot.server.common.dao.JpaAbstractDao;
import com.iot.server.dao.entity.relation.RelationCompositeKey;
import com.iot.server.dao.entity.relation.RelationEntity;
import com.iot.server.dao.repository.RelationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class RelationDaoImpl implements RelationDao {

    private final RelationRepository relationRepository;

    @Override
    public List<RelationEntity> findAll() {
        log.trace("Executing");
        return relationRepository.findAll();
    }

    @Override
    public RelationEntity findById(RelationCompositeKey id) {
        log.trace("{}", id);
        return null;
    }

    @Override
    @Transactional
    public RelationEntity save(RelationEntity entity) {
        log.trace("{}", entity);
        return relationRepository.save(entity);
    }

    @Override
    @Transactional
    public boolean removeById(RelationCompositeKey id) {
        log.trace("{}", id);
        relationRepository.deleteById(id);
        return !relationRepository.existsById(id);
    }

    @Override
    public List<RelationEntity> findAllByFromIds(List<UUID> fromIds) {
        log.trace("{}", fromIds);
        return relationRepository.findAllByFromIdIn(fromIds);
    }
}
