package com.iot.server.dao;

import com.iot.server.common.dao.Dao;
import com.iot.server.common.dto.BaseDto;
import com.iot.server.common.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public abstract class JpaAbstractDao<E extends BaseEntity<D>, D extends BaseDto, ID>
        implements Dao<D, ID> {

    protected abstract Class<E> getEntityClass();

    protected abstract JpaRepository<E, ID> getJpaRepository();

    @Override
    public List<D> findAll() {
        log.debug("Executing");
        List<E> entities = getJpaRepository().findAll();
        return DaoUtil.getDtos(entities);
    }

    @Override
    public D findById(ID id) {
        log.debug("{}", id);
        Optional<E> entity = getJpaRepository().findById(id);
        return DaoUtil.getDto(entity);
    }

    @Override
    @Transactional
    public D save(D dto) {
        log.debug("{}", dto);

        E entity;
        try {
            entity = getEntityClass().getConstructor(dto.getClass()).newInstance(dto);
        } catch (Exception exception) {
            log.error("Can't create entity from dto [{}]", dto, exception);
            throw new IllegalArgumentException("Can't create entity from dto [" + dto + "]");
        }

        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
            entity.setCreatedAt(LocalDateTime.now());
        }

        entity.setUpdatedAt(LocalDateTime.now());
        return DaoUtil.getDto(
                getJpaRepository().save(entity)
        );
    }

    @Transactional
    public List<D> saveAll(Collection<D> dtos) {
        log.debug("{}", dtos);

        List<D> result = new ArrayList<>();
        dtos.forEach(dto -> {
            E entity;
            try {
                entity = getEntityClass().getConstructor(dto.getClass()).newInstance(dto);
            } catch (Exception exception) {
                log.error("Can't create entity from dto [{}]", dto, exception);
                throw new IllegalArgumentException("Can't create entity from dto [" + dto + "]");
            }

            if (entity.getId() == null) {
                entity.setId(UUID.randomUUID());
            }
            result.add(
                    DaoUtil.getDto(
                            getJpaRepository().saveAndFlush(entity)
                    ));
        });
        return result;
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