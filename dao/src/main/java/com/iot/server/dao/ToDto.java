package com.iot.server.dao;

import com.iot.server.common.dto.BaseDto;

public interface ToDto<T extends BaseDto> {
    T toDto();
}
