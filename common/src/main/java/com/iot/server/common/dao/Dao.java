package com.iot.server.common.dao;


import com.iot.server.common.entity.BaseEntity;

import java.util.List;

public interface Dao<E extends BaseEntity, ID> {

    List<E> findAll();

    E findById(ID id);

    E save(E entity);

    boolean removeById(ID id);

}
