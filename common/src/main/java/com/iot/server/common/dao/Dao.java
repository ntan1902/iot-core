package com.iot.server.common.dao;


import com.iot.server.common.dto.BaseDto;
import java.util.List;

public interface Dao<D extends BaseDto, ID> {

  List<D> findAll();

  D findById(ID id);

  D save(D dto);

  boolean removeById(ID id);

}
