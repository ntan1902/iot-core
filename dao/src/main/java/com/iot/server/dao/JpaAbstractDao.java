package com.iot.server.dao;

import com.iot.server.common.dao.Dao;
import com.iot.server.common.dto.BaseDto;
import com.iot.server.dao.entity.BaseEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
public abstract class JpaAbstractDao<E extends BaseEntity<D>, D extends BaseDto, ID>
        implements Dao<D, ID> {

    protected abstract Class<E> getEntityClass();

    protected abstract JpaRepository<E, ID> getJpaRepository();

    @Override
    public List<D> findAll() {
        log.debug("Executing findAll");
        List<E> entities = getJpaRepository().findAll();
        return DaoUtil.getDtos(entities);
    }

    @Override
    public D findById(ID id) {
        log.debug("Executing findById [{}]", id);
        Optional<E> entity = getJpaRepository().findById(id);
        return DaoUtil.getDto(entity);
    }

    @Override
    @Transactional
    public D save(D dto) {
        log.debug("Executing save [{}]", dto);

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


        return DaoUtil.getDto(
                getJpaRepository().save(entity)
        );
    }

    @Override
    @Transactional
    public boolean removeById(ID id) {
        log.debug("Executing removeById [{}]", id);
        getJpaRepository().deleteById(id);
        return !getJpaRepository().existsById(id);
    }

}