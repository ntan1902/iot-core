package com.iot.server.common.entity;

import com.iot.server.common.dto.BaseDto;

public interface ToDto<T> {
    T toDto();
}
