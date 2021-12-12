package com.iot.server.dao.db;

import com.iot.server.common.dao.db.Dao;
import com.iot.server.common.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Slf4j
public abstract class JpaAbstractDao<E extends BaseEntity<ID>, ID extends Serializable> implements Dao<E, ID> {

    protected abstract JpaRepository<E, ID> getJpaRepository();

    @Override
    public List<E> findAll() {
        log.debug("Executing");
        return getJpaRepository().findAll();
    }

    @Override
    public E findById(ID id) {
        log.debug("{}", id);
        return getJpaRepository().findById(id).orElse(null);
    }

    @Override
    @Transactional
    public E save(E entity) {
        log.debug("{}", entity);
        if (entity.getId() == null) {
            entity.setCreatedAt(LocalDateTime.now());
        }

        entity.setUpdatedAt(LocalDateTime.now());
        return getJpaRepository().save(entity);
    }

    @Override
    @Transactional
    public boolean removeById(ID id) {
        log.debug("{}", id);
        getJpaRepository().deleteById(id);
        return !getJpaRepository().existsById(id);
    }

    @Transactional
    public void removeAllByIds(Collection<ID> ids) {
        log.debug("{}", ids);
        JpaRepository<E, ID> repository = getJpaRepository();
        ids.forEach(repository::deleteById);
    }
}